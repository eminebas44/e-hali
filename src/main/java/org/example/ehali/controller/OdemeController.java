package org.example.ehali.controller;

import org.example.ehali.entity.Odeme;
import org.example.ehali.service.OdemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/odemeler")
public class OdemeController {

    private final OdemeService odemeService;

    @Autowired
    public OdemeController(OdemeService odemeService) {
        this.odemeService = odemeService;
    }

    @GetMapping
    public ResponseEntity<List<Odeme>> getAllOdemeler() {
        List<Odeme> odemeler = odemeService.findAll();
        return new ResponseEntity<>(odemeler, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Odeme> getOdemeById(@PathVariable Long id) {
        return odemeService.findById(id)
                .map(odeme -> new ResponseEntity<>(odeme, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Odeme> createOdeme(@RequestBody Odeme odeme) {
        try {
            Odeme yeniOdeme = odemeService.odemeYapVeKaydet(odeme);
            return new ResponseEntity<>(yeniOdeme, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Odeme> updateOdeme(@PathVariable Long id, @RequestBody Odeme odemeDetails) {
        try {
            Odeme guncelOdeme = odemeService.update(id, odemeDetails);
            return new ResponseEntity<>(guncelOdeme, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteOdeme(@PathVariable Long id) {
        try {
            odemeService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
