package org.example.ehali.controller;

import lombok.RequiredArgsConstructor;
import org.example.ehali.dto.AuthRequest;
import org.example.ehali.dto.RegisterRequest;
import org.example.ehali.guvenlik.JwtServisi;
import org.example.ehali.repository.KullaniciRepository;
import org.example.ehali.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5174") // CORS Hatasını çözen kritik satır
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final KullaniciRepository kullaniciRepository;
    private final JwtServisi jwtServisi;
    private final AuthenticationService authenticationService;

    /**
     * Yeni Kullanıcı Kaydı
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            var response = authenticationService.register(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Kayıt sırasında bir hata oluştu: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * Kullanıcı Girişi (Login)
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            // 1. Kimlik Doğrulama
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            // 2. Kullanıcıyı Getirme
            var user = kullaniciRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

            // 3. Token Oluşturma
            var jwtToken = jwtServisi.tokenOlustur(user);

            // 4. Yanıt Hazırlama
            Map<String, String> response = new HashMap<>();
            response.put("token", jwtToken);
            response.put("email", user.getEmail());
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