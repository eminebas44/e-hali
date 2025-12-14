package org.example.ehali.service;

import org.example.ehali.entity.Satici;
import org.example.ehali.repository.SaticiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaticiService {

    private final SaticiRepository saticiRepository;

    @Autowired
    public SaticiService(SaticiRepository saticiRepository) {
        this.saticiRepository = saticiRepository;
    }

    public List<Satici> findAll() {
        return saticiRepository.findAll();
    }

    public Optional<Satici> findById(Long id) {
        return saticiRepository.findById(id);
    }

    public Satici save(Satici satici) {
        return saticiRepository.save(satici);
    }

    public void deleteById(Long id) {
        saticiRepository.deleteById(id);
    }
    
    public Satici update(Long id, Satici saticiDetails) {
        Satici satici = saticiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Satıcı bulunamadı: " + id));

        // Sadece Satıcı'ya ait profil bilgilerini güncelle
        satici.setAd(saticiDetails.getAd());
        satici.setSoyad(saticiDetails.getSoyad());
        satici.setTelefon(saticiDetails.getTelefon());
        
        // Email ve şifre gibi kimlik bilgileri Kullanici entity'sinde olduğu için
        // bu serviste güncellenmemelidir. Onlar için ayrı bir KullaniciServisi veya
        // KimlikDogrulamaServisi'nde metotlar olmalıdır.
        
        return saticiRepository.save(satici);
    }
}
