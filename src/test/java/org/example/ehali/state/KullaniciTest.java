package org.example.ehali.state;

import org.example.ehali.entity.Kullanici;
import org.example.ehali.entity.Rol;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KullaniciTest {

    @Test
    void kullaniciOlusturmaTesti() {
        Kullanici kullanici = Kullanici.builder()
                .id(1L)
                .ad("Emine")
                .soyad("Bas")
                .email("emine@example.com")
                .sifre("1234")
                .rol(Rol.MUSTERI)
                .build();

        assertEquals("Emine", kullanici.getAd());
        assertEquals("emine@example.com", kullanici.getUsername());
        assertNotNull(kullanici.getId());
    }

    @Test
    void yetkiKontrolTesti() {
        Kullanici kullanici = Kullanici.builder()
                .rol(Rol.ADMIN)
                .build();

        boolean hasAdminRole = kullanici.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        assertTrue(hasAdminRole);
    }
}
