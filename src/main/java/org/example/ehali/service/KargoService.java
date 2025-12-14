package org.example.ehali.service;

import org.example.ehali.entity.Kargo;
import org.example.ehali.repository.KargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KargoService {

    private final KargoRepository kargoRepository;

    @Autowired
    public KargoService(KargoRepository kargoRepository) {
        this.kargoRepository = kargoRepository;
    }

    public List<Kargo> findAll() {
        return kargoRepository.findAll();
    }

    public Optional<Kargo> findById(Long id) {
        return kargoRepository.findById(id);
    }

    public Kargo save(Kargo kargo) {
        return kargoRepository.save(kargo);
    }

    public void deleteById(Long id) {
        kargoRepository.deleteById(id);
    }

    public Kargo update(Long id, Kargo kargoDetails) {
        Kargo kargo = kargoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kargo bulunamadı: " + id));

        kargo.setSiparis(kargoDetails.getSiparis());
        kargo.setFirmaAdi(kargoDetails.getFirmaAdi());
        kargo.setTakipNo(kargoDetails.getTakipNo());
        kargo.setGonderimTarihi(kargoDetails.getGonderimTarihi());
        kargo.setTeslimTarihi(kargoDetails.getTeslimTarihi());

        return kargoRepository.save(kargo);
    }
}
