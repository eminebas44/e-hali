package org.example.ehali.controller;

import org.example.ehali.entity.Favori;
import org.example.ehali.entity.FavoriId;
import org.example.ehali.service.FavoriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favoriler")
public class FavoriController {

    private final FavoriService favoriService;

    @Autowired
    public FavoriController(FavoriService favoriService) {
        this.favoriService = favoriService;
    }

    @GetMapping
    public ResponseEntity<List<Favori>> getAllFavoriler() {
        List<Favori> favoriler = favoriService.findAll();
        return new ResponseEntity<>(favoriler, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Favori> createFavori(@RequestBody Favori favori) {
        Favori yeniFavori = favoriService.save(favori);
        return new ResponseEntity<>(yeniFavori, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteFavori(@RequestBody FavoriId favoriId) {
        try {
            favoriService.deleteById(favoriId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
