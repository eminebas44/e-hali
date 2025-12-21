package org.example.ehali.controller;

import org.example.ehali.dto.HaliDTO;
import org.example.ehali.dto.HaliGetirDTO;
import org.example.ehali.entity.Hali;
import org.example.ehali.service.HaliService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/halilar")
@CrossOrigin(origins = "http://localhost:5174")
public class HaliController {

    private final HaliService haliService;

    public HaliController(HaliService haliService) {
        this.haliService = haliService;
    }

    @GetMapping("/kategori/{kategoriAdi}")
    public ResponseEntity<List<HaliGetirDTO>> getByKategori(
            @PathVariable String kategoriAdi) {

        return ResponseEntity.ok(
                haliService.findByKategoriAdi(kategoriAdi)
        );
    }

    @GetMapping
    public ResponseEntity<List<HaliGetirDTO>> getAll() {
        return ResponseEntity.ok(
                haliService.findAllHalilarAsDTO()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hali> getById(@PathVariable Long id) {
        return haliService.findById(id)
                .map(h -> new ResponseEntity<>(h, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
