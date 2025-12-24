package org.example.ehali.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.ehali.entity.*;
import org.example.ehali.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // Güvenlik filtrelerini kapatır
@ActiveProfiles("test")
@Transactional
public class OdemeControllerIT {

    @Autowired private MockMvc mockMvc;
    @Autowired private SiparisRepository siparisRepository;
    @Autowired private KullaniciRepository kullaniciRepository;
    @Autowired private MusteriRepository musteriRepository;
    @Autowired private ObjectMapper objectMapper;

    private Siparis setupSiparis;

    @BeforeEach
    void setUp() {
        Kullanici k = new Kullanici();
        k.setAd("Test"); k.setSoyad("User");
        k.setEmail("pay" + System.nanoTime() + "@test.com");
        k.setSifre("123"); k.setRol(Rol.MUSTERI);
        k = kullaniciRepository.save(k);

        Musteri m = new Musteri();
        m.setKullanici(k);
        m = musteriRepository.save(m);

        setupSiparis = new Siparis();
        setupSiparis.setMusteri(m);
        setupSiparis.setToplamTutar(BigDecimal.valueOf(2000));
        setupSiparis = siparisRepository.save(setupSiparis);
    }

    @Test
    void createOdeme_YeniOdemeOlusturmali() throws Exception {
        Odeme yeni = new Odeme();
        yeni.setTutar(BigDecimal.valueOf(100.00));

        // Sadece ID'si olan temiz bir Siparis nesnesi gönderiyoruz
        // ki Jackson yetki (Authority) hatası vermesin
        Siparis gonderilecekSiparis = new Siparis();
        gonderilecekSiparis.setSiparisId(setupSiparis.getSiparisId());
        yeni.setSiparis(gonderilecekSiparis);

        mockMvc.perform(post("/api/odemeler")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(yeni)))
                .andExpect(status().isCreated());
    }
}