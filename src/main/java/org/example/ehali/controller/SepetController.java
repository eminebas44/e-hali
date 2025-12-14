package org.example.ehali.controller;

import org.example.ehali.entity.Sepet;
import org.example.ehali.service.SepetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sepet")
public class SepetController {

    private final SepetService sepetService;

    public SepetController(SepetService sepetService) {
        this.sepetService = sepetService;
    }

    @GetMapping("/{musteriId}")
    public ResponseEntity<Sepet> sepetiGetir(@PathVariable Long musteriId) {
        return ResponseEntity.ok(sepetService.getSepetByMusteri(musteriId));
    }

    @PostMapping("/{musteriId}/ekle")
    public ResponseEntity<String> sepeteEkle(@PathVariable Long musteriId,
                                             @RequestParam Long haliId,
                                             @RequestParam int adet) {
        sepetService.sepeteEkle(musteriId, haliId, adet);
        return ResponseEntity.ok("Ürün sepete eklendi.");
    }

    @DeleteMapping("/{musteriId}/cikar/{haliId}")
    public ResponseEntity<String> sepettenCikar(@PathVariable Long musteriId,
                                                @PathVariable Long haliId) {
        sepetService.sepettenCikar(musteriId, haliId);
        return ResponseEntity.ok("Ürün sepetten çıkarıldı.");
    }

    @PostMapping("/{musteriId}/onayla")
    public ResponseEntity<String> sepetiOnayla(@PathVariable Long musteriId) {
        Long siparisId = sepetService.sepetiOnaylaVeSiparisVer(musteriId);
        return ResponseEntity.ok("Sipariş başarıyla oluşturuldu. Sipariş No: " + siparisId);
    }
}