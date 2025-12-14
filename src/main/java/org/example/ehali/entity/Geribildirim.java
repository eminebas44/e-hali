package org.example.ehali.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "geribildirim")
public class Geribildirim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "geribildirim_id")
    private Long geribildirimId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "musteri_id", nullable = false)
    private Musteri musteri;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "satici_id", nullable = false)
    private Satici satici;

    @Lob
    @Column(name = "yorum")
    private String yorum;

    @Column(name = "tarih")
    private LocalDate tarih;

    public Geribildirim() {
    }

    public Long getGeribildirimId() {
        return geribildirimId;
    }

    public void setGeribildirimId(Long geribildirimId) {
        this.geribildirimId = geribildirimId;
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

    public String getYorum() {
        return yorum;
    }

    public void setYorum(String yorum) {
        this.yorum = yorum;
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
