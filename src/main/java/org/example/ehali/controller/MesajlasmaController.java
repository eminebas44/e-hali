package org.example.ehali.controller;

import org.example.ehali.entity.Mesajlasma;
import org.example.ehali.service.MesajlasmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mesajlar")
public class MesajlasmaController {

    private final MesajlasmaService mesajlasmaService;

    @Autowired
    public MesajlasmaController(MesajlasmaService mesajlasmaService) {
        this.mesajlasmaService = mesajlasmaService;
    }

    @GetMapping
    public ResponseEntity<List<Mesajlasma>> getAllMesajlar() {
        List<Mesajlasma> mesajlar = mesajlasmaService.findAll();
        return new ResponseEntity<>(mesajlar, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mesajlasma> getMesajById(@PathVariable Long id) {
        return mesajlasmaService.findById(id)
                .map(mesaj -> new ResponseEntity<>(mesaj, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Mesajlasma> createMesaj(@RequestBody Mesajlasma mesajlasma) {
        Mesajlasma yeniMesaj = mesajlasmaService.save(mesajlasma);
        return new ResponseEntity<>(yeniMesaj, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mesajlasma> updateMesaj(@PathVariable Long id, @RequestBody Mesajlasma mesajlasmaDetails) {
        try {
            Mesajlasma guncelMesaj = mesajlasmaService.update(id, mesajlasmaDetails);
            return new ResponseEntity<>(guncelMesaj, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteMesaj(@PathVariable Long id) {
        try {
            mesajlasmaService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
