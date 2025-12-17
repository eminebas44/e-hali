package org.example.ehali.controller;

import lombok.RequiredArgsConstructor;
import org.example.ehali.dto.AuthRequest;
import org.example.ehali.dto.RegisterRequest; // Yeni DTO'yu import ettik
import org.example.ehali.guvenlik.JwtServisi;
import org.example.ehali.repository.KullaniciRepository;
import org.example.ehali.service.AuthenticationService; // Servisimizi import ettik
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final KullaniciRepository kullaniciRepository;
    private final JwtServisi jwtServisi;
    private final AuthenticationService authenticationService; // Servisi inject ettik

    @PostMapping("/register") // YENİ KAYIT ENDPOINT'İ
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            // Gelen isteği servis katmanına yönlendiriyoruz
            var response = authenticationService.register(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Hata durumunda anlamlı bir mesaj dönüyoruz
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Kayıt sırasında bir hata oluştu: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            var user = kullaniciRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

            var jwtToken = jwtServisi.tokenOlustur(user);

            Map<String, String> response = new HashMap<>();
            response.put("token", jwtToken);
            response.put("role", user.getRol().toString());

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Hatalı e-posta veya şifre.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Sunucu tarafında bir hata oluştu: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}