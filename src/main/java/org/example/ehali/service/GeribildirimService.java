package org.example.ehali.service;

import org.example.ehali.entity.Geribildirim;
import org.example.ehali.repository.GeribildirimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GeribildirimService {

    private final GeribildirimRepository geribildirimRepository;

    @Autowired
    public GeribildirimService(GeribildirimRepository geribildirimRepository) {
        this.geribildirimRepository = geribildirimRepository;
    }

    public List<Geribildirim> findAll() {
        return geribildirimRepository.findAll();
    }

    public Optional<Geribildirim> findById(Long id) {
        return geribildirimRepository.findById(id);
    }

    public Geribildirim save(Geribildirim geribildirim) {
        return geribildirimRepository.save(geribildirim);
    }

    public void deleteById(Long id) {
        geribildirimRepository.deleteById(id);
    }

    public Geribildirim update(Long id, Geribildirim geribildirimDetails) {
        Geribildirim geribildirim = geribildirimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Geribildirim bulunamadı: " + id));

        geribildirim.setMusteri(geribildirimDetails.getMusteri());
        geribildirim.setSatici(geribildirimDetails.getSatici());
        geribildirim.setYorum(geribildirimDetails.getYorum());

        return geribildirimRepository.save(geribildirim);
    }
}
