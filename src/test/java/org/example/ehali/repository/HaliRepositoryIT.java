package org.example.ehali.repository;

import org.example.ehali.entity.Hali;
import org.example.ehali.entity.Kategori;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class HaliRepositoryIT {

    @Autowired
    private HaliRepository haliRepository;

    @Autowired
    private KategoriRepository kategoriRepository;

    private Kategori ipekKategori;

    @BeforeEach
    void setUp() {
        Kategori kategori = new Kategori();
        kategori.setKategoriAdi("İpek");
        ipekKategori = kategoriRepository.save(kategori);
    }

    @Test
    void save_YeniHaliVeritabaninaKaydedilmeli() {
        Hali hali = new Hali();
        hali.setTur("Hereke");
        hali.setFiyat(BigDecimal.valueOf(5000));
        hali.setKategori(ipekKategori);

        Hali kaydedilen = haliRepository.save(hali);

        assertNotNull(kaydedilen.getHaliId());
        assertEquals("Hereke", kaydedilen.getTur());
    }

    @Test
    void findByKategoriIn_KategoriyeGoreHaliGetirmeli() {
        Hali hali = new Hali();
        hali.setTur("Uşak");
        hali.setKategori(ipekKategori);
        haliRepository.save(hali);

        List<Hali> sonuc = haliRepository.findByKategoriIn(List.of(ipekKategori));

        assertFalse(sonuc.isEmpty());
        assertEquals(1, sonuc.size());
        assertEquals("Uşak", sonuc.get(0).getTur());
    }

    @Test
    void findById_OlmayanIdIcinEmptyDonmeli() {
        Optional<Hali> sonuc = haliRepository.findById(999L);
        assertTrue(sonuc.isEmpty());
    }
}