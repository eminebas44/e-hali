package org.example.ehali.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration; // Import gerekli
import org.springframework.web.cors.CorsConfigurationSource; // Import gerekli
import org.springframework.web.cors.UrlBasedCorsConfigurationSource; // Import gerekli
import java.util.Arrays; // Import gerekli

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. CORS'u aktif et ve corsConfigurationSource Bean'ini kullan
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 2. CSRF'i kapat
                .csrf(csrf -> csrf.disable())

                // 3. Yetkilendirme Kuralları
                .authorizeHttpRequests(auth -> auth
                        // Auth (Giriş/Kayıt) işlemlerine izin ver (permitAll() ile)
                        .requestMatchers("/api/auth/**").permitAll()

                        // Swagger UI ve API Dokümantasyonuna izin ver
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // Geri kalan her yer için giriş yapılmış olsun
                        .anyRequest().authenticated()
                );

        return http.build();
    }

    /**
     * Frontend adresine izin veren standart CORS konfigürasyonu.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Frontend adresine izin ver
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}