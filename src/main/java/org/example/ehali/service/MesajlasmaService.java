package org.example.ehali.service;

import org.example.ehali.entity.Mesajlasma;
import org.example.ehali.repository.MesajlasmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MesajlasmaService {

    private final MesajlasmaRepository mesajlasmaRepository;

    @Autowired
    public MesajlasmaService(MesajlasmaRepository mesajlasmaRepository) {
        this.mesajlasmaRepository = mesajlasmaRepository;
    }

    public List<Mesajlasma> findAll() {
        return mesajlasmaRepository.findAll();
    }

    public Optional<Mesajlasma> findById(Long id) {
        return mesajlasmaRepository.findById(id);
    }

    public Mesajlasma save(Mesajlasma mesajlasma) {
        return mesajlasmaRepository.save(mesajlasma);
    }

    public void deleteById(Long id) {
        mesajlasmaRepository.deleteById(id);
    }

    public Mesajlasma update(Long id, Mesajlasma mesajlasmaDetails) {
        Mesajlasma mesajlasma = mesajlasmaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesaj bulunamadı: " + id));

        mesajlasma.setMusteri(mesajlasmaDetails.getMusteri());
        mesajlasma.setSatici(mesajlasmaDetails.getSatici());
        mesajlasma.setMesajIcerik(mesajlasmaDetails.getMesajIcerik());

        return mesajlasmaRepository.save(mesajlasma);
    }
}
