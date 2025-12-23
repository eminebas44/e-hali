package org.example.ehali.service;

import org.example.ehali.entity.*;
import org.example.ehali.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SepetServiceTest {

    @Mock
    private SepetRepository sepetRepository;
    @Mock
    private HaliRepository haliRepository;
    @Mock
    private MusteriRepository musteriRepository;
    @Mock
    private SiparisRepository siparisRepository;
    @Mock
    private SiparisDetayRepository siparisDetayRepository;

    @InjectMocks
    private SepetService sepetService;

    private Musteri testMusteri;
    private Sepet testSepet;
    private Hali testHali;

    @BeforeEach
    void setUp() {
        testMusteri = new Musteri();
        testMusteri.setMusteriId(1L);

        testSepet = new Sepet();
        testSepet.setId(100L);
        testSepet.setMusteri(testMusteri);
        testSepet.setSepetDetaylari(new ArrayList<>());

        testHali = new Hali();
        testHali.setHaliId(50L);
        testHali.setTur("Yün Halı");
        testHali.setFiyat(BigDecimal.valueOf(1000));
        testHali.setStokAdedi(10);
    }

    @Test
    void sepeteEkle_StokYetersizse_HataFirlatmali() {
        when(sepetRepository.findByMusteri_MusteriId(1L)).thenReturn(Optional.of(testSepet));
        when(haliRepository.findById(50L)).thenReturn(Optional.of(testHali));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            sepetService.sepeteEkle(1L, 50L, 15);
        });

        assertTrue(exception.getMessage().contains("Yetersiz Stok"));
        verify(sepetRepository, never()).save(any());
    }

    @Test
    void sepetiOnaylaVeSiparisVer_SepetBosssa_HataFirlatmali() {
        when(sepetRepository.findByMusteri_MusteriId(1L)).thenReturn(Optional.of(testSepet));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            sepetService.sepetiOnaylaVeSiparisVer(1L);
        });

        assertEquals("Sepetiniz boş, sipariş oluşturulamaz.", exception.getMessage());
    }

    @Test
    void sepetiOnaylaVeSiparisVer_Basariliysa_StokDusmeli() {
        SepetDetay detay = new SepetDetay();
        detay.setHali(testHali);
        detay.setAdet(2);
        testSepet.getSepetDetaylari().add(detay);

        Siparis sahteSiparis = new Siparis();
        sahteSiparis.setSiparisId(500L);

        when(sepetRepository.findByMusteri_MusteriId(1L)).thenReturn(Optional.of(testSepet));
        when(siparisRepository.save(any(Siparis.class))).thenReturn(sahteSiparis);

        Long siparisId = sepetService.sepetiOnaylaVeSiparisVer(1L);

        assertEquals(500L, siparisId);
        assertEquals(8, testHali.getStokAdedi());
        verify(haliRepository).save(testHali);
        verify(sepetRepository, atLeastOnce()).save(testSepet);
    }
}