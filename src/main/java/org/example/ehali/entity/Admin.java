package org.example.ehali.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long adminId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "kullanici_id", referencedColumnName = "id")
    private Kullanici kullanici;

    @Column(name = "ad", nullable = false, length = 100)
    private String ad;

    @Column(name = "soyad", nullable = false, length = 100)
    private String soyad;

    @Column(name = "kayit_tarihi")
    private LocalDate kayitTarihi;

    public Admin() {
    }

    public Admin(Kullanici kullanici, String ad, String soyad) {
        this.kullanici = kullanici;
        this.ad = ad;
        this.soyad = soyad;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
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
        return "Admin{" +
               "adminId=" + adminId +
               ", kullanici=" + (kullanici != null ? kullanici.getEmail() : "null") +
               ", ad='" + ad + '\'' +
               ", soyad='" + soyad + '\'' +
               ", kayitTarihi=" + kayitTarihi +
               '}';
    }
}
