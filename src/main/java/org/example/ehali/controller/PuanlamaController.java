package org.example.ehali.controller;

import org.example.ehali.entity.Puanlama;
import org.example.ehali.service.PuanlamaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/puanlamalar")
public class PuanlamaController {

    private final PuanlamaService puanlamaService;

    @Autowired
    public PuanlamaController(PuanlamaService puanlamaService) {
        this.puanlamaService = puanlamaService;
    }

    @GetMapping
    public ResponseEntity<List<Puanlama>> getAllPuanlamalar() {
        List<Puanlama> puanlamalar = puanlamaService.findAll();
        return new ResponseEntity<>(puanlamalar, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Puanlama> getPuanlamaById(@PathVariable Long id) {
        return puanlamaService.findById(id)
                .map(puanlama -> new ResponseEntity<>(puanlama, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Puanlama> createPuanlama(@RequestBody Puanlama puanlama) {
        Puanlama yeniPuanlama = puanlamaService.save(puanlama);
        return new ResponseEntity<>(yeniPuanlama, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Puanlama> updatePuanlama(@PathVariable Long id, @RequestBody Puanlama puanlamaDetails) {
        try {
            Puanlama guncelPuanlama = puanlamaService.update(id, puanlamaDetails);
            return new ResponseEntity<>(guncelPuanlama, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePuanlama(@PathVariable Long id) {
        try {
            puanlamaService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
