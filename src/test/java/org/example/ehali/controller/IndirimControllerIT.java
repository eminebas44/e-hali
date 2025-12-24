package org.example.ehali.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.ehali.entity.Indirim;
import org.example.ehali.repository.IndirimRepository;
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
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class IndirimControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IndirimRepository indirimRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Indirim testIndirim;

    @BeforeEach
    void setUp() {
        testIndirim = new Indirim();
        testIndirim.setDeger(BigDecimal.valueOf(15.0));
        testIndirim.setStratejiTipi("Sabit");
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
        Indirim yeniIndirim = new Indirim();
        yeniIndirim.setDeger(BigDecimal.valueOf(10.0));
        yeniIndirim.setStratejiTipi("Yuzde");

        mockMvc.perform(post("/api/indirimler")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(yeniIndirim)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.indirimId").exists());
    }

    @Test
    void deleteIndirim_NoContentDonmeli() throws Exception {
        mockMvc.perform(delete("/api/indirimler/{id}", testIndirim.getIndirimId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void getIndirimliFiyat_HaliBulunamazsaNotFoundDonmeli() throws Exception {
        mockMvc.perform(get("/api/indirimler/hesapla/{haliId}", 9999L))
                .andExpect(status().isNotFound());
    }
}