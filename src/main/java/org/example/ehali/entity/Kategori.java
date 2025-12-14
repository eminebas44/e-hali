package org.example.ehali.entity;

import jakarta.persistence.*;
import org.example.ehali.entity.composite.KategoriBileseni;

@Entity
@Table(name = "kategori")
public class Kategori implements KategoriBileseni {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kategori_id")
    private Long kategoriId;

    @Column(name = "kategori_adi", nullable = false, length = 100)
    private String kategoriAdi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ust_kategori_id")
    private Kategori ustKategori;

    public Kategori() {
    }

    public Kategori(String kategoriAdi) {
        this.kategoriAdi = kategoriAdi;
    }

    @Override
    public String getAd() {
        return this.kategoriAdi;
    }

    @Override
    public void yazdir() {
        System.out.println(" - " + getAd());
    }

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

    @Override
    public String toString() {
        return "Kategori{" +
               "kategoriId=" + kategoriId +
               ", kategoriAdi='" + kategoriAdi + '\'' +
               '}';
    }
}
