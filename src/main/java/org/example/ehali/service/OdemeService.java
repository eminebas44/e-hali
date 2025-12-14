package org.example.ehali.service;

import org.example.ehali.entity.Odeme;
import org.example.ehali.repository.OdemeRepository;
import org.example.ehali.service.payment.OdemeIslemciFactory;
import org.example.ehali.service.payment.OdemeIslemcisi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdemeService {

    private final OdemeRepository odemeRepository;
    private final OdemeIslemciFactory odemeIslemciFactory;

    @Autowired
    public OdemeService(OdemeRepository odemeRepository, OdemeIslemciFactory odemeIslemciFactory) {
        this.odemeRepository = odemeRepository;
        this.odemeIslemciFactory = odemeIslemciFactory;
    }

    public Odeme odemeYapVeKaydet(Odeme odeme) {
        OdemeIslemcisi islemci = odemeIslemciFactory.getIslemci(odeme.getOdemeTipi());
        if (islemci == null) {
            throw new IllegalArgumentException("Geçersiz ödeme tipi: " + odeme.getOdemeTipi());
        }

        islemci.odemeYap(odeme.getTutar());

        return odemeRepository.save(odeme);
    }

    public List<Odeme> findAll() {
        return odemeRepository.findAll();
    }

    public Optional<Odeme> findById(Long id) {
        return odemeRepository.findById(id);
    }

    public void deleteById(Long id) {
        odemeRepository.deleteById(id);
    }

    public Odeme update(Long id, Odeme odemeDetails) {
        Odeme odeme = odemeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ödeme bulunamadı: " + id));

        odeme.setSiparis(odemeDetails.getSiparis());
        odeme.setOdemeTipi(odemeDetails.getOdemeTipi());
        odeme.setTutar(odemeDetails.getTutar());

        return odemeRepository.save(odeme);
    }
}
