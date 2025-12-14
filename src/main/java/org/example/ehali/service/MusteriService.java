package org.example.ehali.service;

import org.example.ehali.entity.Musteri;
import org.example.ehali.repository.MusteriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MusteriService {

    private final MusteriRepository musteriRepository;

    @Autowired
    public MusteriService(MusteriRepository musteriRepository) {
        this.musteriRepository = musteriRepository;
    }

    public List<Musteri> findAll() {
        return musteriRepository.findAll();
    }

    public Optional<Musteri> findById(Long id) {
        return musteriRepository.findById(id);
    }

    public Musteri save(Musteri musteri) {
        return musteriRepository.save(musteri);
    }

    public void deleteById(Long id) {
        musteriRepository.deleteById(id);
    }
}