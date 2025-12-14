package org.example.ehali.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "favori")
public class Favori {

    @EmbeddedId
    private FavoriId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("musteriId")
    @JoinColumn(name = "musteri_id")
    private Musteri musteri;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("haliId")
    @JoinColumn(name = "hali_id")
    private Hali hali;

    @Column(name = "eklenme_tarihi")
    private LocalDate eklenmeTarihi;

    public Favori() {
    }

    public FavoriId getId() {
        return id;
    }

    public void setId(FavoriId id) {
        this.id = id;
    }

    public Musteri getMusteri() {
        return musteri;
    }

    public void setMusteri(Musteri musteri) {
        this.musteri = musteri;
    }

    public Hali getHali() {
        return hali;
    }

    public void setHali(Hali hali) {
        this.hali = hali;
    }

    public LocalDate getEklenmeTarihi() {
        return eklenmeTarihi;
    }

    public void setEklenmeTarihi(LocalDate eklenmeTarihi) {
        this.eklenmeTarihi = eklenmeTarihi;
    }

    @PrePersist
    protected void onCreate() {
        if (eklenmeTarihi == null) {
            eklenmeTarihi = LocalDate.now();
        }
    }
}
