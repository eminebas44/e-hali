package org.example.ehali.guvenlik;

import jakarta.validation.Valid; // <-- BU İMPORT ÖNEMLİ
import lombok.RequiredArgsConstructor;
import org.example.ehali.guvenlik.dto.GirisIstegi;
import org.example.ehali.guvenlik.dto.KayitIstegi;
import org.example.ehali.guvenlik.dto.KimlikDogrulamaYaniti;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class KimlikDogrulamaController {

    private final KimlikDogrulamaServisi service;

    @PostMapping("/kayit")
    public ResponseEntity<KimlikDogrulamaYaniti> kayitOl(@Valid @RequestBody KayitIstegi istek) {
        return ResponseEntity.ok(service.kayit(istek));
    }

    @PostMapping("/giris")
    public ResponseEntity<KimlikDogrulamaYaniti> girisYap(@RequestBody GirisIstegi istek) {
        return ResponseEntity.ok(service.giris(istek));
    }
}