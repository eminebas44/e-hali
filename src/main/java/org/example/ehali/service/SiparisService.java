package org.example.ehali.service;

import org.example.ehali.entity.Siparis;
import org.example.ehali.repository.SiparisRepository;
import org.springframework.stereotype.Service;
import org.example.ehali.entity.state.SiparisDurumu;

import java.util.List;
import java.util.Optional;

@Service
public class SiparisService {

    private final SiparisRepository siparisRepository;

    public SiparisService(SiparisRepository siparisRepository) {
        this.siparisRepository = siparisRepository;
    }

    public List<Siparis> findAll() {
        return siparisRepository.findAll();
    }

    public Optional<Siparis> findById(Long id) {
        return siparisRepository.findById(id);
    }

    public Siparis save(Siparis siparis) {
        return siparisRepository.save(siparis);
    }

    public void deleteById(Long id) {
        siparisRepository.deleteById(id);
    }

    public Siparis update(Long id, Siparis siparisDetails) {
        return siparisRepository.findById(id).map(mevcutSiparis -> {
            mevcutSiparis.setToplamTutar(siparisDetails.getToplamTutar());
            mevcutSiparis.setDurumAdi(siparisDetails.getDurumAdi());
            return siparisRepository.save(mevcutSiparis);
        }).orElseThrow(() -> new RuntimeException("Sipariş bulunamadı id: " + id));
    }

    public Siparis sonrakiSiparisDurumu(Long id) {
        Siparis siparis = siparisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sipariş bulunamadı"));

        siparis.sonrakiDurum();
        siparis.setDurumAdi(siparis.getDurum().getDurumAdi());
        return siparisRepository.save(siparis);
    }

    public Siparis iptalEtSiparis(Long id) {
        Siparis siparis = siparisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sipariş bulunamadı"));

        siparis.iptalEt();
        siparis.setDurumAdi(siparis.getDurum().getDurumAdi());
        return siparisRepository.save(siparis);
    }
}