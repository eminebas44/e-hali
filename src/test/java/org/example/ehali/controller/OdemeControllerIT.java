package org.example.ehali.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.ehali.entity.Odeme;
import org.example.ehali.repository.OdemeRepository;
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
public class OdemeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OdemeRepository odemeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Odeme mevcutOdeme;

    @BeforeEach
    void setUp() {
        mevcutOdeme = new Odeme();
        mevcutOdeme.setTutar(BigDecimal.valueOf(2000.00));
        mevcutOdeme = odemeRepository.save(mevcutOdeme);
    }

    @Test
    void getAllOdemeler_BasariliDonmeli() throws Exception {
        mockMvc.perform(get("/api/odemeler"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    void createOdeme_YeniOdemeOlusturmali() throws Exception {
        Odeme yeni = new Odeme();
        yeni.setTutar(BigDecimal.valueOf(100.00));

        mockMvc.perform(post("/api/odemeler")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(yeni)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.odemeId").exists());
    }
}