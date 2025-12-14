package org.example.ehali.controller;

import org.example.ehali.entity.Geribildirim;
import org.example.ehali.service.GeribildirimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/geribildirimler")
public class GeribildirimController {

    private final GeribildirimService geribildirimService;

    @Autowired
    public GeribildirimController(GeribildirimService geribildirimService) {
        this.geribildirimService = geribildirimService;
    }

    @GetMapping
    public ResponseEntity<List<Geribildirim>> getAllGeribildirimler() {
        List<Geribildirim> geribildirimler = geribildirimService.findAll();
        return new ResponseEntity<>(geribildirimler, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Geribildirim> getGeribildirimById(@PathVariable Long id) {
        return geribildirimService.findById(id)
                .map(geribildirim -> new ResponseEntity<>(geribildirim, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Geribildirim> createGeribildirim(@RequestBody Geribildirim geribildirim) {
        Geribildirim yeniGeribildirim = geribildirimService.save(geribildirim);
        return new ResponseEntity<>(yeniGeribildirim, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Geribildirim> updateGeribildirim(@PathVariable Long id, @RequestBody Geribildirim geribildirimDetails) {
        try {
            Geribildirim guncelGeribildirim = geribildirimService.update(id, geribildirimDetails);
            return new ResponseEntity<>(guncelGeribildirim, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteGeribildirim(@PathVariable Long id) {
        try {
            geribildirimService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
