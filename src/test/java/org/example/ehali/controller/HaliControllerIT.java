package org.example.ehali.controller;

import org.example.ehali.entity.Hali;
import org.example.ehali.entity.Kategori;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class HaliControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HaliRepository haliRepository;

    @Autowired
    private KategoriRepository kategoriRepository;

    private Kategori testKategori;
    private Hali testHali;

    @BeforeEach
    void setUp() {
        testKategori = new Kategori();
        testKategori.setKategoriAdi("Yünlü");
        testKategori = kategoriRepository.save(testKategori);

        testHali = new Hali();
        testHali.setTur("El Dokuması");
        testHali.setFiyat(BigDecimal.valueOf(3500));
        testHali.setKategori(testKategori);
        testHali = haliRepository.save(testHali);
    }

    @Test
    void getAll_HalilariListelemeli() throws Exception {
        mockMvc.perform(get("/api/halilar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].tur", notNullValue()));
    }

    @Test
    void getById_MevcutHaliyiGetirmeli() throws Exception {
        mockMvc.perform(get("/api/halilar/{id}", testHali.getHaliId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.haliId", is(testHali.getHaliId().intValue())))
                .andExpect(jsonPath("$.tur", is("El Dokuması")));
    }

    @Test
    void getById_OlmayanIdIcinNotFoundDonmeli() throws Exception {
        mockMvc.perform(get("/api/halilar/{id}", 9999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getByKategori_KategoriyeGoreListelemeli() throws Exception {
        mockMvc.perform(get("/api/halilar/kategori/{kategoriAdi}", "Yünlü")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }
}