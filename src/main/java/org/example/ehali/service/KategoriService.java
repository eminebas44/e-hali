package org.example.ehali.service;

import org.example.ehali.entity.Kategori;
import org.example.ehali.entity.composite.BilesikKategori;
import org.example.ehali.repository.KategoriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KategoriService {

    private final KategoriRepository kategoriRepository;

    @Autowired
    public KategoriService(KategoriRepository kategoriRepository) {
        this.kategoriRepository = kategoriRepository;
    }

    public List<Kategori> findAll() {
        return kategoriRepository.findAll();
    }

    public Optional<Kategori> findById(Long id) {
        return kategoriRepository.findById(id);
    }

    public Kategori save(Kategori kategori) {
        return kategoriRepository.save(kategori);
    }

    public void deleteById(Long id) {
        kategoriRepository.deleteById(id);
    }

    public Kategori update(Long id, Kategori kategoriDetails) {
        Kategori kategori = kategoriRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kategori bulunamadı: " + id));

        kategori.setKategoriAdi(kategoriDetails.getKategoriAdi());
        kategori.setUstKategori(kategoriDetails.getUstKategori());

        return kategoriRepository.save(kategori);
    }

    public void kategoriHiyerarsisiniYazdir() {
        List<Kategori> tumKategoriler = kategoriRepository.findAll();
        BilesikKategori anaDugum = new BilesikKategori("Tüm Kategoriler");


        for (Kategori kategori : tumKategoriler) {
            if (kategori.getUstKategori() == null) {
                BilesikKategori ustKategoriBileseni = new BilesikKategori(kategori.getAd());
                for (Kategori altKategori : tumKategoriler) {
                    if (altKategori.getUstKategori() != null && altKategori.getUstKategori().getKategoriId().equals(kategori.getKategoriId())) {
                        ustKategoriBileseni.ekle(altKategori);
                    }
                }
                anaDugum.ekle(ustKategoriBileseni);
            }
        }
        anaDugum.yazdir();
    }
}
