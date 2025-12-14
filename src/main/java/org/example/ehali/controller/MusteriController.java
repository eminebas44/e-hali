package org.example.ehali.controller;

import org.example.ehali.entity.Musteri;
import org.example.ehali.service.MusteriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/musteriler")
public class MusteriController {

    private final MusteriService musteriService;

    @Autowired
    public MusteriController(MusteriService musteriService) {
        this.musteriService = musteriService;
    }

    @GetMapping
    public List<Musteri> getAllMusteriler() {
        return musteriService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Musteri> getMusteriById(@PathVariable Long id) {
        return musteriService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Musteri createMusteri(@RequestBody Musteri musteri) {
        return musteriService.save(musteri);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMusteri(@PathVariable Long id) {
        musteriService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}