package org.example.ehali.service;

import org.example.ehali.entity.Favori;
import org.example.ehali.entity.FavoriId;
import org.example.ehali.repository.FavoriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriService {

    private final FavoriRepository favoriRepository;

    @Autowired
    public FavoriService(FavoriRepository favoriRepository) {
        this.favoriRepository = favoriRepository;
    }

    public List<Favori> findAll() {
        return favoriRepository.findAll();
    }

    public Optional<Favori> findById(FavoriId id) {
        return favoriRepository.findById(id);
    }

    public Favori save(Favori favori) {
        return favoriRepository.save(favori);
    }

    public void deleteById(FavoriId id) {
        favoriRepository.deleteById(id);
    }
}
