package org.example.ehali.service;

import org.example.ehali.entity.Hali;
import org.example.ehali.entity.Indirim;
import org.example.ehali.repository.HaliRepository;
import org.example.ehali.repository.IndirimRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IndirimServiceTest {

    @Mock
    private IndirimRepository indirimRepository;

    @Mock
    private HaliRepository haliRepository;

    @InjectMocks
    private IndirimService indirimService;

    private Hali testHali;
    private Indirim testIndirim;

    @BeforeEach
    void setUp() {
        testHali = new Hali();
        testHali.setHaliId(1L);
        testHali.setFiyat(BigDecimal.valueOf(1000));

        testIndirim = new Indirim();
        testIndirim.setIndirimId(10L);
        testIndirim.setHali(testHali);
        testIndirim.setDeger(BigDecimal.valueOf(20));
        testIndirim.setStratejiTipi("YUZDESEL");
        testIndirim.setBaslangicTarihi(LocalDate.now().minusDays(5));
        testIndirim.setBitisTarihi(LocalDate.now().plusDays(5));
    }

    @Test
    void indirimliFiyatHesapla_AktifYuzdeselIndirimVarsa_IndirimliFiyatDonmeli() {
        when(haliRepository.findById(1L)).thenReturn(Optional.of(testHali));
        when(indirimRepository.findAll()).thenReturn(List.of(testIndirim));

        BigDecimal sonuc = indirimService.indirimliFiyatHesapla(1L);

        assertNotNull(sonuc);
        assertTrue(sonuc.compareTo(BigDecimal.valueOf(1000)) < 0);
        verify(haliRepository).findById(1L);
    }

    @Test
    void indirimliFiyatHesapla_IndirimSuresiDolmussa_OrijinalFiyatDonmeli() {
        testIndirim.setBaslangicTarihi(LocalDate.now().minusDays(10));
        testIndirim.setBitisTarihi(LocalDate.now().minusDays(1));

        when(haliRepository.findById(1L)).thenReturn(Optional.of(testHali));
        when(indirimRepository.findAll()).thenReturn(List.of(testIndirim));

        BigDecimal sonuc = indirimService.indirimliFiyatHesapla(1L);

        assertEquals(0, sonuc.compareTo(BigDecimal.valueOf(1000)));
        verify(indirimRepository).findAll();
    }
}