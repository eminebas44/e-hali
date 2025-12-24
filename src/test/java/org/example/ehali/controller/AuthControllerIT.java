package org.example.ehali.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.ehali.dto.AuthRequest;
import org.example.ehali.dto.RegisterRequest;
import org.example.ehali.entity.Kullanici;
import org.example.ehali.entity.Rol;
import org.example.ehali.repository.KullaniciRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class AuthControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KullaniciRepository kullaniciRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        kullaniciRepository.deleteAll();
    }

    @Test
    void register_YeniKullaniciKaydiBasariliOlmalı() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("yeni@ehali.com");
        request.setSifre("sifre123");
        request.setAd("Emine");
        request.setSoyad("Bas");
        request.setRol(Rol.MUSTERI);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void login_GecerliBilgilerleTokenDonmeli() throws Exception {
        Kullanici kullanici = new Kullanici();
        kullanici.setAd("Test");
        kullanici.setSoyad("User");
        kullanici.setEmail("test@ehali.com");
        kullanici.setSifre(passwordEncoder.encode("sifre123"));
        kullanici.setRol(Rol.MUSTERI);
        kullaniciRepository.save(kullanici);

        AuthRequest request = new AuthRequest();
        request.setEmail("test@ehali.com");
        request.setPassword("sifre123");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.email", org.hamcrest.Matchers.is("test@ehali.com")))
                .andExpect(jsonPath("$.role", org.hamcrest.Matchers.is("MUSTERI")));
    }

    @Test
    void login_HataliSifreIleUnauthorizedDonmeli() throws Exception {
        Kullanici kullanici = new Kullanici();
        kullanici.setEmail("hata@ehali.com");
        kullanici.setSifre(passwordEncoder.encode("dogruSifre"));
        kullanici.setAd("Hata");
        kullanici.setSoyad("Test");
        kullanici.setRol(Rol.MUSTERI);
        kullaniciRepository.save(kullanici);

        AuthRequest request = new AuthRequest();
        request.setEmail("hata@ehali.com");
        request.setPassword("yanlisSifre");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Hatalı e-posta veya şifre."));
    }
}