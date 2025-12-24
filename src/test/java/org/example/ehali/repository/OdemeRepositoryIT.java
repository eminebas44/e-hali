package org.example.ehali.repository;

import org.example.ehali.entity.Odeme;
import org.example.ehali.entity.Siparis;
import org.example.ehali.entity.Musteri;
import org.example.ehali.entity.Kullanici;
import org.example.ehali.entity.Rol;
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

    private Odeme testOdemesi;
    private Siparis testSiparis;

    @BeforeEach
    void setUp() {
        Kullanici kullanici = new Kullanici();
        kullanici.setAd("Test");
        kullanici.setSoyad("User");
        kullanici.setEmail("test" + System.currentTimeMillis() + "@ehali.com");
        kullanici.setSifre("123");
        kullanici.setRol(Rol.MUSTERI);
        kullanici = kullaniciRepository.save(kullanici);

        Musteri musteri = new Musteri();
        musteri.setKullanici(kullanici);
        musteri = musteriRepository.save(musteri);

        testSiparis = new Siparis();
        testSiparis.setMusteri(musteri);
        testSiparis.setToplamTutar(BigDecimal.valueOf(2000));
        testSiparis = siparisRepository.save(testSiparis);

        testOdemesi = new Odeme();
        testOdemesi.setTutar(BigDecimal.valueOf(2000.00));
        testOdemesi.setSiparis(testSiparis); 
        testOdemesi = odemeRepository.save(testOdemesi);
    }

    @Test
    void save_YeniOdemeKaydetmeli() {
        Odeme yeni = new Odeme();
        yeni.setTutar(BigDecimal.valueOf(500.00));
        yeni.setSiparis(testSiparis);
        Odeme kaydedilen = odemeRepository.save(yeni);
        assertNotNull(kaydedilen.getOdemeId());
    }

    @Test
    void findById_MevcutOdemeyiGetirmeli() {
        var bulunan = odemeRepository.findById(testOdemesi.getOdemeId());
        assertTrue(bulunan.isPresent());
        assertEquals(0, BigDecimal.valueOf(2000.0).compareTo(bulunan.get().getTutar()));
    }
}