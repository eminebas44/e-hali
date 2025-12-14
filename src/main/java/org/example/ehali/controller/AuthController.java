package org.example.ehali.controller;

import org.example.ehali.config.UygulamaAyarlariYoneticisi;
import org.example.ehali.dto.LoginRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/giris")
    public ResponseEntity<?> girisYap(@RequestBody LoginRequestDTO request) {

        // Singleton üzerinden yönetici e-postasını alıyoruz
        String yoneticiEmail = UygulamaAyarlariYoneticisi.getInstance().getYoneticiEpostasi();
        String varsayilanSifre = "123456"; // Şifreyi de sabit kontrol edelim (Gereksiz DB sorgusunu önlemek için)

        // 1. E-Posta ve Şifre Kontrolü (SADECE Singleton ile)
        if (yoneticiEmail.equals(request.getEmail()) && varsayilanSifre.equals(request.getSifre())) {

            // Başarılı giriş
            Map<String, String> response = new HashMap<>();
            String surum = UygulamaAyarlariYoneticisi.getInstance().getUygulamaSurumu();

            response.put("token", "fake-jwt-token-" + surum);
            response.put("message", "Yönetici Girişi Başarılı! (v" + surum + ")");

            return ResponseEntity.ok(response);
        }

        // 2. Başarısız giriş
        return ResponseEntity.status(401).body("E-Posta veya Şifre hatalı!");
    }
}