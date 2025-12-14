package org.example.ehali.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "puanlama")
public class Puanlama {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "puan_id")
    private Long puanId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "musteri_id", nullable = false)
    private Musteri musteri;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "satici_id", nullable = false)
    private Satici satici;

    @Column(name = "puan")
    private Integer puan;

    @Column(name = "tarih")
    private LocalDate tarih;

    public Puanlama() {
    }

    public Long getPuanId() {
        return puanId;
    }

    public void setPuanId(Long puanId) {
        this.puanId = puanId;
    }

    public Musteri getMusteri() {
        return musteri;
    }

    public void setMusteri(Musteri musteri) {
        this.musteri = musteri;
    }

    public Satici getSatici() {
        return satici;
    }

    public void setSatici(Satici satici) {
        this.satici = satici;
    }

    public Integer getPuan() {
        return puan;
    }

    public void setPuan(Integer puan) {
        this.puan = puan;
    }

    public LocalDate getTarih() {
        return tarih;
    }

    public void setTarih(LocalDate tarih) {
        this.tarih = tarih;
    }

    @PrePersist
    protected void onCreate() {
        if (tarih == null) {
            tarih = LocalDate.now();
        }
    }
}
