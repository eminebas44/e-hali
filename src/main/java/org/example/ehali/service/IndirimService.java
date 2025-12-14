package org.example.ehali.service;

import org.example.ehali.entity.Hali;
import org.example.ehali.entity.Indirim;
import org.example.ehali.repository.HaliRepository;
import org.example.ehali.repository.IndirimRepository;
import org.example.ehali.service.strategy.IndirimStratejisi;
import org.example.ehali.service.strategy.SabitTutarIndirimStratejisi;
import org.example.ehali.service.strategy.YuzdeselIndirimStratejisi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class IndirimService {

    private final IndirimRepository indirimRepository;
    private final HaliRepository haliRepository; // Halı fiyatına erişmek için

    @Autowired
    public IndirimService(IndirimRepository indirimRepository, HaliRepository haliRepository) {
        this.indirimRepository = indirimRepository;
        this.haliRepository = haliRepository;
    }

    public List<Indirim> findAll() {
        return indirimRepository.findAll();
    }

    public Optional<Indirim> findById(Long id) {
        return indirimRepository.findById(id);
    }

    @Transactional
    public Indirim save(Indirim indirim) {
        return indirimRepository.save(indirim);
    }

    @Transactional
    public Indirim update(Long id, Indirim indirimDetails) {
        Indirim indirim = indirimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("İndirim bulunamadı: " + id));

        indirim.setHali(indirimDetails.getHali());
        indirim.setDeger(indirimDetails.getDeger());
        indirim.setStratejiTipi(indirimDetails.getStratejiTipi());
        indirim.setBaslangicTarihi(indirimDetails.getBaslangicTarihi());
        indirim.setBitisTarihi(indirimDetails.getBitisTarihi());

        return indirimRepository.save(indirim);
    }

    public void deleteById(Long id) {
        indirimRepository.deleteById(id);
    }

    public BigDecimal indirimliFiyatHesapla(Long haliId) {
        Hali hali = haliRepository.findById(haliId)
                .orElseThrow(() -> new RuntimeException("Halı bulunamadı: " + haliId));
        BigDecimal orijinalFiyat = hali.getFiyat();

        // Aktif indirimleri bul
        Optional<Indirim> aktifIndirimOpt = indirimRepository.findAll().stream()
                .filter(indirim -> indirim.getHali().getHaliId().equals(haliId) &&
                                   indirim.getBaslangicTarihi().isBefore(LocalDate.now()) &&
                                   indirim.getBitisTarihi().isAfter(LocalDate.now()))
                .findFirst();

        if (aktifIndirimOpt.isPresent()) {
            Indirim aktifIndirim = aktifIndirimOpt.get();
            IndirimStratejisi strateji;

            switch (aktifIndirim.getStratejiTipi()) {
                case "YUZDESEL":
                    strateji = new YuzdeselIndirimStratejisi();
                    break;
                case "SABIT_TUTAR":
                    strateji = new SabitTutarIndirimStratejisi();
                    break;
                default:
                    throw new IllegalArgumentException("Geçersiz indirim stratejisi: " + aktifIndirim.getStratejiTipi());
            }
            return strateji.indirimUygula(orijinalFiyat, aktifIndirim.getDeger());
        }
        return orijinalFiyat; // Aktif indirim yoksa orijinal fiyatı döndür
    }
}
