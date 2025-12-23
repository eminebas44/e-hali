package org.example.ehali.service;

import org.example.ehali.entity.Musteri;
import org.example.ehali.entity.Puanlama;
import org.example.ehali.entity.Satici;
import org.example.ehali.repository.PuanlamaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PuanlamaServiceTest {

    @Mock
    private PuanlamaRepository puanlamaRepository;

    @InjectMocks
    private PuanlamaService puanlamaService;

    private Puanlama testPuanlama;
    private Musteri testMusteri;
    private Satici testSatici;

    @BeforeEach
    void setUp() {
        testMusteri = new Musteri();
        testMusteri.setMusteriId(1L);

        testSatici = new Satici();
        testSatici.setSaticiId(10L);

        testPuanlama = new Puanlama();
        testPuanlama.setPuanId(100L);
        testPuanlama.setMusteri(testMusteri);
        testPuanlama.setSatici(testSatici);
        testPuanlama.setPuan(5);
    }

    @Test
    void save_GecerliPuanlama_BasariylaKaydetmeli() {
        when(puanlamaRepository.save(any(Puanlama.class))).thenReturn(testPuanlama);

        Puanlama sonuc = puanlamaService.save(testPuanlama);

        assertNotNull(sonuc);
        assertEquals(5, sonuc.getPuan());
        verify(puanlamaRepository).save(testPuanlama);
    }

    @Test
    void update_MevcutIdIle_Guncellemeli() {
        Puanlama guncelDetaylar = new Puanlama();
        guncelDetaylar.setPuan(4);
        guncelDetaylar.setMusteri(testMusteri);
        guncelDetaylar.setSatici(testSatici);

        when(puanlamaRepository.findById(100L)).thenReturn(Optional.of(testPuanlama));
        when(puanlamaRepository.save(any(Puanlama.class))).thenReturn(testPuanlama);

        Puanlama sonuc = puanlamaService.update(100L, guncelDetaylar);

        assertNotNull(sonuc);
        assertEquals(4, sonuc.getPuan());
        verify(puanlamaRepository).save(testPuanlama);
    }

    @Test
    void update_OlmayanIdIle_HataFirlatmali() {
        when(puanlamaRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            puanlamaService.update(999L, testPuanlama);
        });

        verify(puanlamaRepository, never()).save(any());
    }
}