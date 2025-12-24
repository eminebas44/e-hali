package org.example.ehali.repository;

import org.example.ehali.entity.Odeme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class OdemeRepositoryIT {

    @Autowired
    private OdemeRepository odemeRepository;

    private Odeme testOdemesi;

    @BeforeEach
    void setUp() {
        testOdemesi = new Odeme();
        testOdemesi.setTutar(BigDecimal.valueOf(1500.00));
        testOdemesi = odemeRepository.save(testOdemesi);
    }

    @Test
    void save_YeniOdemeKaydetmeli() {
        Odeme yeni = new Odeme();
        yeni.setTutar(BigDecimal.valueOf(500.00));

        Odeme kaydedilen = odemeRepository.save(yeni);

        assertNotNull(kaydedilen.getOdemeId());
        assertEquals(0, BigDecimal.valueOf(500.00).compareTo(kaydedilen.getTutar()));
    }

    @Test
    void findById_MevcutOdemeyiGetirmeli() {
        Optional<Odeme> bulunan = odemeRepository.findById(testOdemesi.getOdemeId());

        assertTrue(bulunan.isPresent());
        assertEquals(0, BigDecimal.valueOf(1500.00).compareTo(bulunan.get().getTutar()));
    }
}