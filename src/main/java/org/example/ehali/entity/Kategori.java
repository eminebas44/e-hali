package org.example.ehali.entity;

import jakarta.persistence.*;
import org.example.ehali.entity.composite.KategoriBileseni;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "kategori")
public class Kategori implements KategoriBileseni {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kategori_id")
    private Long kategoriId;

    @Column(name = "kategori_adi", nullable = false, length = 100)
    private String kategoriAdi;

    // Üst kategori
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ust_kategori_id")
    private Kategori ustKategori;

    // ✅ ALT KATEGORİLER (Composite)
    @OneToMany(mappedBy = "ustKategori", cascade = CascadeType.ALL)
    private List<Kategori> altKategoriler = new ArrayList<>();

    public Kategori() {}

    public Kategori(String kategoriAdi) {
        this.kategoriAdi = kategoriAdi;
    }

    @Override
    public String getAd() {
        return kategoriAdi;
    }

    @Override
    public void yazdir() {
        System.out.println(" - " + kategoriAdi);
        for (Kategori alt : altKategoriler) {
            alt.yazdir();
        }
    }

    // --- Getter & Setter ---
    public Long getKategoriId() {
        return kategoriId;
    }

    public void setKategoriId(Long kategoriId) {
        this.kategoriId = kategoriId;
    }

    public String getKategoriAdi() {
        return kategoriAdi;
    }

    public void setKategoriAdi(String kategoriAdi) {
        this.kategoriAdi = kategoriAdi;
    }

    public Kategori getUstKategori() {
        return ustKategori;
    }

    public void setUstKategori(Kategori ustKategori) {
        this.ustKategori = ustKategori;
    }

    public List<Kategori> getAltKategoriler() {
        return altKategoriler;
    }

    public void setAltKategoriler(List<Kategori> altKategoriler) {
        this.altKategoriler = altKategoriler;
    }
}
