package org.example.ehali.service;

import org.example.ehali.entity.Puanlama;
import org.example.ehali.repository.PuanlamaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PuanlamaService {

    private final PuanlamaRepository puanlamaRepository;

    @Autowired
    public PuanlamaService(PuanlamaRepository puanlamaRepository) {
        this.puanlamaRepository = puanlamaRepository;
    }

    public List<Puanlama> findAll() {
        return puanlamaRepository.findAll();
    }

    public Optional<Puanlama> findById(Long id) {
        return puanlamaRepository.findById(id);
    }

    public Puanlama save(Puanlama puanlama) {
        return puanlamaRepository.save(puanlama);
    }

    public void deleteById(Long id) {
        puanlamaRepository.deleteById(id);
    }

    public Puanlama update(Long id, Puanlama puanlamaDetails) {
        Puanlama puanlama = puanlamaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Puanlama bulunamadı: " + id));

        puanlama.setMusteri(puanlamaDetails.getMusteri());
        puanlama.setSatici(puanlamaDetails.getSatici());
        puanlama.setPuan(puanlamaDetails.getPuan());

        return puanlamaRepository.save(puanlama);
    }
}
