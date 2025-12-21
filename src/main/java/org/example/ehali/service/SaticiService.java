package org.example.ehali.service;

import lombok.RequiredArgsConstructor;
import org.example.ehali.entity.Satici;
import org.example.ehali.repository.SaticiRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SaticiService {

    private final SaticiRepository saticiRepository;

    public List<Satici> findAll() {
        return saticiRepository.findAll();
    }

    // Optional döndürüyoruz ki Controller'da .map() çalışabilsin
    public Optional<Satici> findById(Long id) {
        return saticiRepository.findById(id);
    }

    @Transactional
    public Satici save(Satici satici) {
        return saticiRepository.save(satici);
    }

    @Transactional
    public Satici update(Long id, Satici guncelSatici) {
        Satici mevcutSatici = saticiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Satıcı bulunamadı ID: " + id));

        mevcutSatici.setTelefon(guncelSatici.getTelefon());

        // Kullanıcı bilgilerini güncelleme mantığı
        if (mevcutSatici.getKullanici() != null && guncelSatici.getKullanici() != null) {
            mevcutSatici.getKullanici().setAd(guncelSatici.getKullanici().getAd());
            mevcutSatici.getKullanici().setSoyad(guncelSatici.getKullanici().getSoyad());
        }

        return saticiRepository.save(mevcutSatici);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!saticiRepository.existsById(id)) {
            throw new RuntimeException("Silinecek satıcı bulunamadı!");
        }
        saticiRepository.deleteById(id);
    }
}