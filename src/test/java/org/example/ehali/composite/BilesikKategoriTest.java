package org.example.ehali.composite;

import org.example.ehali.entity.composite.BilesikKategori;
import org.example.ehali.entity.composite.KategoriBileseni;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BilesikKategoriTest {

    @Test
    void altKategoriEklemeTesti() {
        BilesikKategori anaKategori = new BilesikKategori("Halılar");

        KategoriBileseni cocuk1 = new BilesikKategori("Yün");
        KategoriBileseni cocuk2 = new BilesikKategori("İpek");

        anaKategori.ekle(cocuk1);
        anaKategori.ekle(cocuk2);

        assertEquals(2, anaKategori.getAltKategoriler().size());
    }
}
