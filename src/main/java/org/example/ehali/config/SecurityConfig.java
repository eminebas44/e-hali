package org.example.ehali.config;

import org.example.ehali.guvenlik.JwtKimlikDogrulamaFiltresi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtKimlikDogrulamaFiltresi jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        // 1. Herkese Açık Yollar (Giriş yapmadan görülebilir)
                        .requestMatchers(
                                "/api/auth/**",
                                "/api/halilar/**",
                                "/api/kategoriler/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // 2. Sadece SATICI'lara Özel Yollar
                        // (Örneğin: Ürün ekleme, stok güncelleme)
                        .requestMatchers("/api/satici/**").hasRole("SATICI")

                        // 3. Sadece MUSTERI'lere Özel Yollar
                        // (Örneğin: Sipariş verme, sepetim)
                        .requestMatchers("/api/musteri/**").hasRole("MUSTERI")

                        // 4. Sadece ADMIN'e Özel Yollar
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // 5. Geri kalan tüm istekler için sadece giriş yapmış olmak yeterli
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // React portların (5174 ve 5173'ü ekleyelim, Vite bazen değişebilir)
        configuration.setAllowedOrigins(List.of("http://localhost:5174",  "http://localhost:3000"));

        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Cache-Control"));
        configuration.setAllowCredentials(true); // Token bazlı işlemler için önemli

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}