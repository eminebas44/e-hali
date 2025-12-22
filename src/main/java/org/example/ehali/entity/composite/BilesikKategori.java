package org.example.ehali.entity.composite;

import java.util.ArrayList;
import java.util.List;

public class BilesikKategori implements KategoriBileseni {

    private String ad;
    private List<KategoriBileseni> altKategoriler = new ArrayList<>();

    public BilesikKategori(String ad) {
        this.ad = ad;
    }

    public void ekle(KategoriBileseni kategori) {
        altKategoriler.add(kategori);
    }

    public void cikar(KategoriBileseni kategori) {
        altKategoriler.remove(kategori);
    }

    public List<KategoriBileseni> getAltKategoriler() {
        return altKategoriler;
    }

    @Override
    public String getAd() {
        return ad;
    }

    @Override
    public void yazdir() {
        System.out.println("+ " + ad);
        for (KategoriBileseni alt : altKategoriler) {
            alt.yazdir();
        }
    }
}