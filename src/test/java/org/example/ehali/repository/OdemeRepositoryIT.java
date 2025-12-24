package org.example.ehali.repository;

import org.example.ehali.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class OdemeRepositoryIT {

    @Autowired private OdemeRepository odemeRepository;
    @Autowired private SiparisRepository siparisRepository;
    @Autowired private KullaniciRepository kullaniciRepository;
    @Autowired private MusteriRepository musteriRepository;

    private Siparis yeniSiparisOlustur() {
        Kullanici k = new Kullanici();
        k.setAd("Test"); k.setSoyad("User");
        k.setEmail("user" + System.nanoTime() + "@test.com"); // Benzersiz email
        k.setSifre("123"); k.setRol(Rol.MUSTERI);
        k = kullaniciRepository.save(k);

        Musteri m = new Musteri();
        m.setKullanici(k);
        m = musteriRepository.save(m);

        Siparis s = new Siparis();
        s.setMusteri(m);
        s.setToplamTutar(BigDecimal.valueOf(100));
        return siparisRepository.save(s);
    }

    @Test
    void save_YeniOdemeKaydetmeli() {
        Siparis s = yeniSiparisOlustur();
        Odeme o = new Odeme();
        o.setTutar(BigDecimal.valueOf(500));
        o.setSiparis(s);

        Odeme kaydedilen = odemeRepository.save(o);
        assertNotNull(kaydedilen.getOdemeId());
    }

    @Test
    void findById_MevcutOdemeyiGetirmeli() {
        Siparis s = yeniSiparisOlustur();
        Odeme o = new Odeme();
        o.setTutar(BigDecimal.valueOf(1500));
        o.setSiparis(s);
        o = odemeRepository.save(o);

        var bulunan = odemeRepository.findById(o.getOdemeId());
        assertTrue(bulunan.isPresent());
    }
}