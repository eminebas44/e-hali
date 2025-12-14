package org.example.ehali.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "satici")
public class Satici {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "satici_id")
    private Long saticiId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "kullanici_id", referencedColumnName = "id")
    private Kullanici kullanici;

    @Column(name = "ad", nullable = false, length = 100)
    private String ad;

    @Column(name = "soyad", nullable = false, length = 100)
    private String soyad;

    @Column(name = "telefon", length = 20)
    private String telefon;

    @Column(name = "kayit_tarihi")
    private LocalDate kayitTarihi;

    public Satici() {
    }

    public Satici(Kullanici kullanici, String ad, String soyad, String telefon, LocalDate kayitTarihi) {
        this.kullanici = kullanici;
        this.ad = ad;
        this.soyad = soyad;
        this.telefon = telefon;
        this.kayitTarihi = kayitTarihi;
    }

    public Long getSaticiId() {
        return saticiId;
    }

    public void setSaticiId(Long saticiId) {
        this.saticiId = saticiId;
    }

    public Kullanici getKullanici() {
        return kullanici;
    }

    public void setKullanici(Kullanici kullanici) {
        this.kullanici = kullanici;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public LocalDate getKayitTarihi() {
        return kayitTarihi;
    }

    public void setKayitTarihi(LocalDate kayitTarihi) {
        this.kayitTarihi = kayitTarihi;
    }

    @PrePersist
    protected void onCreate() {
        if (kayitTarihi == null) {
            kayitTarihi = LocalDate.now();
        }
    }

    @Override
    public String toString() {
        return "Satici{" +
               "saticiId=" + saticiId +
               ", kullanici=" + (kullanici != null ? kullanici.getEmail() : "null") +
               ", ad='" + ad + '\'' +
               ", soyad='" + soyad + '\'' +
               ", telefon='" + telefon + '\'' +
               ", kayitTarihi=" + kayitTarihi +
               '}';
    }
}
