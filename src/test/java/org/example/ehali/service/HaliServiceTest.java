package org.example.ehali.service;

import org.example.ehali.dto.HaliDTO;
import org.example.ehali.entity.Hali;
import org.example.ehali.entity.Kategori;
import org.example.ehali.entity.Satici;
import org.example.ehali.repository.HaliRepository;
import org.example.ehali.repository.KategoriRepository;
import org.example.ehali.repository.SaticiRepository;
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
public class HaliServiceTest {

    @Mock
    private HaliRepository haliRepository;

    @Mock
    private KategoriRepository kategoriRepository;

    @Mock
    private SaticiRepository saticiRepository;

    @InjectMocks
    private HaliService haliService;

    private Kategori testKategorisi;
    private Satici testSaticisi;
    private HaliDTO testHaliDTO;

    @BeforeEach
    void setUp() {
        testKategorisi = new Kategori();
        testKategorisi.setKategoriId(1L);
        testKategorisi.setKategoriAdi("El Dokuma");

        testSaticisi = new Satici();
        testSaticisi.setSaticiId(10L);

        testHaliDTO = new HaliDTO();
        testHaliDTO.setKategoriId(1L);
        testHaliDTO.setSaticiId(10L);
        testHaliDTO.setTur("El Dokuma");
        testHaliDTO.setFiyat(BigDecimal.valueOf(7500.0));
        testHaliDTO.setStokAdedi(5);
    }

    @Test
    void findByKategoriAdi_KategoriMevcutDegilse_HataFirlatmali() {
        String kategoriAdi = "BilinmeyenKategori";
        when(kategoriRepository.findByKategoriAdiIgnoreCase(kategoriAdi)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            haliService.findByKategoriAdi(kategoriAdi);
        });

        assertEquals("Kategori bulunamadı: " + kategoriAdi, exception.getMessage());
        verify(kategoriRepository).findByKategoriAdiIgnoreCase(kategoriAdi);
    }

    @Test
    void createHaliFromDTO_GecerliVerilerle_BasariylaKaydetmeli() {
        when(kategoriRepository.findById(1L)).thenReturn(Optional.of(testKategorisi));
        when(saticiRepository.findById(10L)).thenReturn(Optional.of(testSaticisi));
        when(haliRepository.save(any(Hali.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Hali kaydedilenHali = haliService.createHaliFromDTO(testHaliDTO);

        assertNotNull(kaydedilenHali);
        assertEquals(testKategorisi, kaydedilenHali.getKategori());
        assertEquals(testSaticisi, kaydedilenHali.getSatici());
        assertEquals(BigDecimal.valueOf(7500.0), kaydedilenHali.getFiyat());
        verify(haliRepository, times(1)).save(any(Hali.class));
    }
}