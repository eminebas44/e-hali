package org.example.ehali.service;

import org.example.ehali.entity.Siparis;
import org.example.ehali.repository.SiparisRepository;
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
public class SiparisServiceTest {

    @Mock
    private SiparisRepository siparisRepository;

    @InjectMocks
    private SiparisService siparisService;

    private Siparis testSiparis;

    @BeforeEach
    void setUp() {
        testSiparis = new Siparis();
        testSiparis.setSiparisId(1L);
        testSiparis.setToplamTutar(BigDecimal.valueOf(2500.0));
    }

    @Test
    void sonrakiSiparisDurumu_GecerliId_DurumuGuncellemeli() {
        when(siparisRepository.findById(1L)).thenReturn(Optional.of(testSiparis));
        when(siparisRepository.save(any(Siparis.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Siparis guncelSiparis = siparisService.sonrakiSiparisDurumu(1L);

        assertNotNull(guncelSiparis);
        assertNotNull(guncelSiparis.getDurumAdi());
        verify(siparisRepository).save(testSiparis);
    }

    @Test
    void iptalEtSiparis_GecerliId_IptalEdildiOlarakIsaretlemeli() {
        when(siparisRepository.findById(1L)).thenReturn(Optional.of(testSiparis));
        when(siparisRepository.save(any(Siparis.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Siparis iptalEdilenSiparis = siparisService.iptalEtSiparis(1L);

        assertNotNull(iptalEdilenSiparis);
        verify(siparisRepository).save(testSiparis);
    }

    @Test
    void update_OlmayanId_HataFirlatmali() {
        when(siparisRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            siparisService.update(99L, new Siparis());
        });

        verify(siparisRepository, never()).save(any());
    }
}