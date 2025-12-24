package org.example.ehali.repository;

import org.example.ehali.entity.Indirim;
import org.example.ehali.entity.Hali;
import org.example.ehali.entity.Kategori;
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
public class IndirimRepositoryIT {

    @Autowired private IndirimRepository indirimRepository;
    @Autowired private HaliRepository haliRepository;
    @Autowired private KategoriRepository kategoriRepository;

    private Indirim ortakIndirim;
    private Hali testHali;

    @BeforeEach
    void setUp() {
        Kategori kategori = new Kategori();
        kategori.setKategoriAdi("Test Kategori");
        kategori = kategoriRepository.save(kategori);

        testHali = new Hali();
        testHali.setFiyat(BigDecimal.valueOf(1000));
        testHali.setKategori(kategori);
        testHali = haliRepository.save(testHali);

        ortakIndirim = new Indirim();
        ortakIndirim.setDeger(BigDecimal.valueOf(10.0));
        ortakIndirim.setStratejiTipi("Yuzde");
        ortakIndirim.setHali(testHali);
        ortakIndirim = indirimRepository.save(ortakIndirim);
    }

    @Test
    void findById_MevcutIndirimiGetirmeli() {
        var bulunan = indirimRepository.findById(ortakIndirim.getIndirimId());
        assertTrue(bulunan.isPresent());
        assertEquals(0, BigDecimal.valueOf(10.0).compareTo(bulunan.get().getDeger()));
    }

    @Test
    void save_YeniIndirimKaydetmeli() {
        Indirim yeni = new Indirim();
        yeni.setDeger(BigDecimal.valueOf(50.0));
        yeni.setHali(testHali);
        Indirim kaydedilen = indirimRepository.save(yeni);
        assertNotNull(kaydedilen.getIndirimId());
    }
}