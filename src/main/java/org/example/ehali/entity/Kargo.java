package org.example.ehali.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "kargo")
public class Kargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kargo_id")
    private Long kargoId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "siparis_id", unique = true)
    private Siparis siparis;

    @Column(name = "firma_adi", length = 100)
    private String firmaAdi;

    @Column(name = "takip_no", length = 100)
    private String takipNo;

    @Column(name = "gonderim_tarihi")
    private LocalDate gonderimTarihi;

    @Column(name = "teslim_tarihi")
    private LocalDate teslimTarihi;

    public Kargo() {
    }

    public Long getKargoId() {
        return kargoId;
    }

    public void setKargoId(Long kargoId) {
        this.kargoId = kargoId;
    }

    public Siparis getSiparis() {
        return siparis;
    }

    public void setSiparis(Siparis siparis) {
        this.siparis = siparis;
    }

    public String getFirmaAdi() {
        return firmaAdi;
    }

    public void setFirmaAdi(String firmaAdi) {
        this.firmaAdi = firmaAdi;
    }

    public String getTakipNo() {
        return takipNo;
    }

    public void setTakipNo(String takipNo) {
        this.takipNo = takipNo;
    }

    public LocalDate getGonderimTarihi() {
        return gonderimTarihi;
    }

    public void setGonderimTarihi(LocalDate gonderimTarihi) {
        this.gonderimTarihi = gonderimTarihi;
    }

    public LocalDate getTeslimTarihi() {
        return teslimTarihi;
    }

    public void setTeslimTarihi(LocalDate teslimTarihi) {
        this.teslimTarihi = teslimTarihi;
    }
}
