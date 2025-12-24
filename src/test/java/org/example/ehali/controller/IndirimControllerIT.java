package org.example.ehali.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.ehali.entity.Indirim;
import org.example.ehali.entity.Hali;
import org.example.ehali.entity.Kategori;
import org.example.ehali.repository.IndirimRepository;
import org.example.ehali.repository.HaliRepository;
import org.example.ehali.repository.KategoriRepository;
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
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Transactional
public class IndirimControllerIT {

    @Autowired private MockMvc mockMvc;
    @Autowired private IndirimRepository indirimRepository;
    @Autowired private HaliRepository haliRepository;
    @Autowired private KategoriRepository kategoriRepository;
    @Autowired private ObjectMapper objectMapper;

    private Indirim testIndirim;
    private Hali testHali;

    @BeforeEach
    void setUp() {
        Kategori kat = new Kategori();
        kat.setKategoriAdi("Test Kat " + System.currentTimeMillis());
        kat = kategoriRepository.save(kat);

        testHali = new Hali();
        testHali.setFiyat(BigDecimal.valueOf(1000));
        testHali.setKategori(kat);
        testHali = haliRepository.save(testHali);

        testIndirim = new Indirim();
        testIndirim.setDeger(BigDecimal.valueOf(15.0));
        testIndirim.setStratejiTipi("Sabit");
        testIndirim.setHali(testHali); // İlişki eklendi
        testIndirim = indirimRepository.save(testIndirim);
    }

    @Test
    void getAllIndirimler_BasariliDonmeli() throws Exception {
        mockMvc.perform(get("/api/indirimler"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    void createIndirim_YeniKayitOlusturmali() throws Exception {
        Indirim yeni = new Indirim();
        yeni.setDeger(BigDecimal.valueOf(10.0));
        yeni.setStratejiTipi("Yuzde");
        yeni.setHali(testHali); // İlişki eklendi

        mockMvc.perform(post("/api/indirimler")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(yeni)))
                .andExpect(status().isCreated());
    }
}