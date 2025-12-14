package org.example.ehali.controller;

import org.example.ehali.entity.Indirim;
import org.example.ehali.service.IndirimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/indirimler")
public class IndirimController {

    private final IndirimService indirimService;

    @Autowired
    public IndirimController(IndirimService indirimService) {
        this.indirimService = indirimService;
    }

    @GetMapping
    public ResponseEntity<List<Indirim>> getAllIndirimler() {
        List<Indirim> indirimler = indirimService.findAll();
        return new ResponseEntity<>(indirimler, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Indirim> getIndirimById(@PathVariable Long id) {
        return indirimService.findById(id)
                .map(indirim -> new ResponseEntity<>(indirim, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Indirim> createIndirim(@RequestBody Indirim indirim) {
        Indirim yeniIndirim = indirimService.save(indirim);
        return new ResponseEntity<>(yeniIndirim, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Indirim> updateIndirim(@PathVariable Long id, @RequestBody Indirim indirimDetails) {
        try {
            Indirim guncelIndirim = indirimService.update(id, indirimDetails);
            return new ResponseEntity<>(guncelIndirim, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteIndirim(@PathVariable Long id) {
        try {
            indirimService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/hesapla/{haliId}")
    public ResponseEntity<BigDecimal> getIndirimliFiyat(@PathVariable Long haliId) {
        try {
            BigDecimal indirimliFiyat = indirimService.indirimliFiyatHesapla(haliId);
            return new ResponseEntity<>(indirimliFiyat, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
