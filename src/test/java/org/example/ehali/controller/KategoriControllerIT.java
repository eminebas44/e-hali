package org.example.ehali.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.ehali.entity.Kategori;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class KategoriControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private KategoriRepository kategoriRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Kategori mevcutKategori;

    @BeforeEach
    void setUp() {
        mevcutKategori = new Kategori();
        mevcutKategori.setKategoriAdi("Mutfak Halıları");
        mevcutKategori = kategoriRepository.save(mevcutKategori);
    }

    @Test
    void getAllKategoriler_ListeyiDonmeli() throws Exception {
        mockMvc.perform(get("/api/kategoriler"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].kategoriAdi", notNullValue()));
    }

    @Test
    void getKategoriById_BasariliDonmeli() throws Exception {
        mockMvc.perform(get("/api/kategoriler/{id}", mevcutKategori.getKategoriId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.kategoriId", is(mevcutKategori.getKategoriId().intValue())))
                .andExpect(jsonPath("$.kategoriAdi", is("Mutfak Halıları")));
    }

    @Test
    void createKategori_YeniKayitOlusturmali() throws Exception {
        Kategori yeni = new Kategori();
        yeni.setKategoriAdi("Yolluklar");

        mockMvc.perform(post("/api/kategoriler")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(yeni)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.kategoriId").exists())
                .andExpect(jsonPath("$.kategoriAdi", is("Yolluklar")));
    }

    @Test
    void updateKategori_GuncellemeYapmali() throws Exception {
        mevcutKategori.setKategoriAdi("Guncel Kategori");

        mockMvc.perform(put("/api/kategoriler/{id}", mevcutKategori.getKategoriId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mevcutKategori)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.kategoriAdi", is("Guncel Kategori")));
    }

    @Test
    void deleteKategori_NoContentDonmeli() throws Exception {
        mockMvc.perform(delete("/api/kategoriler/{id}", mevcutKategori.getKategoriId()))
                .andExpect(status().isNoContent());
    }
}