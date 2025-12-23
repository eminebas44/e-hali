package org.example.ehali.state;

import org.example.ehali.entity.Siparis;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class SiparisStateTest {
    
@SpringBootTest
@ActiveProfiles("test")
    void siparisBaslangicDurumuTesti() {
        Siparis siparis = new Siparis();

        assertNotNull(siparis.getDurum());
        assertEquals("Beklemede", siparis.getDurumAdi());
    }

  @SpringBootTest
@ActiveProfiles("test")
    void sonrakiDurumGecisTesti() {
        Siparis siparis = new Siparis();
        siparis.sonrakiDurum();

        assertNotNull(siparis.getDurumAdi());
        assertNotEquals("Beklemede", siparis.getDurumAdi());
    }

@SpringBootTest
@ActiveProfiles("test")
    void siparisIptalEtmeTesti() {
        Siparis siparis = new Siparis();
        siparis.iptalEt();

        assertEquals("İptal Edildi", siparis.getDurumAdi());
    }

@SpringBootTest
@ActiveProfiles("test")
    void siparisTutarVeVeriTesti() {
        Siparis siparis = new Siparis();
        BigDecimal tutar = new BigDecimal("1500.50");

        siparis.setToplamTutar(tutar);

        assertEquals(tutar, siparis.getToplamTutar());
    }
}
