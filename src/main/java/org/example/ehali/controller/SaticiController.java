package org.example.ehali.controller;

import org.example.ehali.entity.Satici;
import org.example.ehali.service.SaticiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saticilar")
public class SaticiController {

    private final SaticiService saticiService;

    @Autowired
    public SaticiController(SaticiService saticiService) {
        this.saticiService = saticiService;
    }

    @GetMapping
    public ResponseEntity<List<Satici>> getAllSaticilar() {
        List<Satici> saticilar = saticiService.findAll();
        return new ResponseEntity<>(saticilar, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Satici> getSaticiById(@PathVariable Long id) {
        return saticiService.findById(id)
                .map(satici -> new ResponseEntity<>(satici, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Satici> createSatici(@RequestBody Satici satici) {
        Satici yeniSatici = saticiService.save(satici);
        return new ResponseEntity<>(yeniSatici, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Satici> updateSatici(@PathVariable Long id, @RequestBody Satici saticiDetails) {
        try {
            Satici güncelSatici = saticiService.update(id, saticiDetails);
            return new ResponseEntity<>(güncelSatici, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSatici(@PathVariable Long id) {
        try {
            saticiService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
