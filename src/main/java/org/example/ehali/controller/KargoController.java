package org.example.ehali.controller;

import org.example.ehali.entity.Kargo;
import org.example.ehali.service.KargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kargolar")
public class KargoController {

    private final KargoService kargoService;

    @Autowired
    public KargoController(KargoService kargoService) {
        this.kargoService = kargoService;
    }

    @GetMapping
    public ResponseEntity<List<Kargo>> getAllKargolar() {
        List<Kargo> kargolar = kargoService.findAll();
        return new ResponseEntity<>(kargolar, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Kargo> getKargoById(@PathVariable Long id) {
        return kargoService.findById(id)
                .map(kargo -> new ResponseEntity<>(kargo, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Kargo> createKargo(@RequestBody Kargo kargo) {
        Kargo yeniKargo = kargoService.save(kargo);
        return new ResponseEntity<>(yeniKargo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Kargo> updateKargo(@PathVariable Long id, @RequestBody Kargo kargoDetails) {
        try {
            Kargo guncelKargo = kargoService.update(id, kargoDetails);
            return new ResponseEntity<>(guncelKargo, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteKargo(@PathVariable Long id) {
        try {
            kargoService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
