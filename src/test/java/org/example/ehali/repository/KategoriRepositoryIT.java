package org.example.ehali.repository;

import org.example.ehali.entity.Kategori;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class KategoriRepositoryIT {

    @Autowired
    private KategoriRepository kategoriRepository;

    private Kategori testKategori;

    @BeforeEach
    void setUp() {
        testKategori = new Kategori();
        testKategori.setKategoriAdi("Yünlü Halılar");
        testKategori = kategoriRepository.save(testKategori);
    }

    @Test
    void findByKategoriAdiIgnoreCase_BasariliDonmeli() {
        Optional<Kategori> bulunan = kategoriRepository.findByKategoriAdiIgnoreCase("yünlü halılar");

        assertTrue(bulunan.isPresent());
        assertEquals(testKategori.getKategoriId(), bulunan.get().getKategoriId());
    }

    @Test
    void save_YeniKategoriKaydetmeli() {
        Kategori yeni = new Kategori();
        yeni.setKategoriAdi("İpek Halılar");
        Kategori kaydedilen = kategoriRepository.save(yeni);

        assertNotNull(kaydedilen.getKategoriId());
        assertEquals("İpek Halılar", kaydedilen.getKategoriAdi());
    }
}