package org.example.ehali.repository;

import org.example.ehali.entity.Indirim;
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

    @Autowired
    private IndirimRepository indirimRepository;

    private Indirim ortakIndirim;

    @BeforeEach
    void setUp() {
        ortakIndirim = new Indirim();
        ortakIndirim.setDeger(BigDecimal.valueOf(10.0));
        ortakIndirim.setStratejiTipi("Yuzde");
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
        Indirim kaydedilen = indirimRepository.save(yeni);
        assertNotNull(kaydedilen.getIndirimId());
    }
}