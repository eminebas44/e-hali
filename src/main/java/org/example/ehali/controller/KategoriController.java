package org.example.ehali.controller;

import org.example.ehali.entity.Kategori;
import org.example.ehali.service.KategoriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kategoriler")
@CrossOrigin(origins = "http://localhost:5174") // React uygulamanın portu (CORS çözümü)
public class KategoriController {

    private final KategoriService kategoriService;

    @Autowired
    public KategoriController(KategoriService kategoriService) {
        this.kategoriService = kategoriService;
    }

    // React tarafındaki kategori butonlarını besleyecek ana endpoint
    @GetMapping
    public ResponseEntity<List<Kategori>> getAllKategoriler() {
        List<Kategori> kategoriler = kategoriService.findAll();
        return new ResponseEntity<>(kategoriler, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Kategori> getKategoriById(@PathVariable Long id) {
        return kategoriService.findById(id)
                .map(kategori -> new ResponseEntity<>(kategori, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Kategori> createKategori(@RequestBody Kategori kategori) {
        Kategori yeniKategori = kategoriService.save(kategori);
        return new ResponseEntity<>(yeniKategori, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Kategori> updateKategori(@PathVariable Long id, @RequestBody Kategori kategoriDetails) {
        try {
            Kategori güncelKategori = kategoriService.update(id, kategoriDetails);
            return new ResponseEntity<>(güncelKategori, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteKategori(@PathVariable Long id) {
        try {
            kategoriService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}