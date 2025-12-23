package org.example.ehali.service;

import org.example.ehali.entity.Odeme;
import org.example.ehali.entity.Siparis;
import org.example.ehali.repository.OdemeRepository;
import org.example.ehali.service.payment.OdemeIslemciFactory;
import org.example.ehali.service.payment.OdemeIslemcisi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OdemeServiceTest {

    @Mock
    private OdemeRepository odemeRepository;

    @Mock
    private OdemeIslemciFactory odemeIslemciFactory;

    @Mock
    private OdemeIslemcisi odemeIslemcisi;

    @InjectMocks
    private OdemeService odemeService;

    private Odeme testOdeme;
    private Siparis testSiparis;

    @BeforeEach
    void setUp() {
        testSiparis = new Siparis();
        testSiparis.setSiparisId(1L);

        testOdeme = new Odeme();
        testOdeme.setOdemeId(100L);
        testOdeme.setOdemeTipi("KREDI_KARTI");
        testOdeme.setTutar(BigDecimal.valueOf(500.0));
        testOdeme.setSiparis(testSiparis);
    }

    @Test
    void odemeYapVeKaydet_GecerliTip_BasariylaKaydetmeli() {
        when(odemeIslemciFactory.getIslemci("KREDI_KARTI")).thenReturn(odemeIslemcisi);
        when(odemeRepository.save(any(Odeme.class))).thenReturn(testOdeme);

        Odeme sonuc = odemeService.odemeYapVeKaydet(testOdeme);

        assertNotNull(sonuc);
        verify(odemeIslemcisi).odemeYap(BigDecimal.valueOf(500.0));
        verify(odemeRepository).save(testOdeme);
    }

    @Test
    void odemeYapVeKaydet_GecersizTip_HataFirlatmali() {
        when(odemeIslemciFactory.getIslemci("GEÇERSİZ")).thenReturn(null);
        testOdeme.setOdemeTipi("GEÇERSİZ");

        assertThrows(IllegalArgumentException.class, () -> {
            odemeService.odemeYapVeKaydet(testOdeme);
        });

        verify(odemeRepository, never()).save(any());
    }

    @Test
    void update_MevcutIdIle_Guncellemeli() {
        Odeme guncelDetaylar = new Odeme();
        guncelDetaylar.setOdemeTipi("HAVALE");
        guncelDetaylar.setTutar(BigDecimal.valueOf(450.0));

        when(odemeRepository.findById(100L)).thenReturn(Optional.of(testOdeme));
        when(odemeRepository.save(any(Odeme.class))).thenReturn(testOdeme);

        Odeme sonuc = odemeService.update(100L, guncelDetaylar);

        assertEquals("HAVALE", sonuc.getOdemeTipi());
        assertEquals(0, sonuc.getTutar().compareTo(BigDecimal.valueOf(450.0)));
        verify(odemeRepository).save(testOdeme);
    }
}