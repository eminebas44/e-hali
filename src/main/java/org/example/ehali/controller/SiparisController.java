package org.example.ehali.controller;

import org.example.ehali.entity.Siparis;
import org.example.ehali.service.SiparisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/siparisler")
public class SiparisController {

    private final SiparisService siparisService;

    @Autowired
    public SiparisController(SiparisService siparisService) {
        this.siparisService = siparisService;
    }

    @GetMapping
    public ResponseEntity<List<Siparis>> getAllSiparisler() {
        List<Siparis> siparisler = siparisService.findAll();
        return new ResponseEntity<>(siparisler, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Siparis> getSiparisById(@PathVariable Long id) {
        return siparisService.findById(id)
                .map(siparis -> new ResponseEntity<>(siparis, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Siparis> createSiparis(@RequestBody Siparis siparis) {
        Siparis yeniSiparis = siparisService.save(siparis);
        return new ResponseEntity<>(yeniSiparis, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/sonraki-durum")
    public ResponseEntity<Siparis> sonrakiSiparisDurumu(@PathVariable Long id) {
        try {
            Siparis guncelSiparis = siparisService.sonrakiSiparisDurumu(id);
            return new ResponseEntity<>(guncelSiparis, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/iptal-et")
    public ResponseEntity<Siparis> iptalEtSiparis(@PathVariable Long id) {
        try {
            Siparis guncelSiparis = siparisService.iptalEtSiparis(id);
            return new ResponseEntity<>(guncelSiparis, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Siparis> updateSiparis(@PathVariable Long id, @RequestBody Siparis siparisDetails) {
        try {
            Siparis guncelSiparis = siparisService.update(id, siparisDetails);
            return new ResponseEntity<>(guncelSiparis, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSiparis(@PathVariable Long id) {
        try {
            siparisService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}