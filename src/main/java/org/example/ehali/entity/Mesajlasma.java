package org.example.ehali.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mesajlasma")
public class Mesajlasma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mesaj_id")
    private Long mesajId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "musteri_id", nullable = false)
    private Musteri musteri;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "satici_id", nullable = false)
    private Satici satici;

    @Lob
    @Column(name = "mesaj_icerik", nullable = false)
    private String mesajIcerik;

    @Column(name = "gonderim_tarihi")
    private LocalDateTime gonderimTarihi;

    public Mesajlasma() {
    }

    public Long getMesajId() {
        return mesajId;
    }

    public void setMesajId(Long mesajId) {
        this.mesajId = mesajId;
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

    public String getMesajIcerik() {
        return mesajIcerik;
    }

    public void setMesajIcerik(String mesajIcerik) {
        this.mesajIcerik = mesajIcerik;
    }


    public LocalDateTime getGonderimTarihi() {
        return gonderimTarihi;
    }

    public void setGonderimTarihi(LocalDateTime gonderimTarihi) {
        this.gonderimTarihi = gonderimTarihi;
    }

    @PrePersist
    protected void onCreate() {
        if (gonderimTarihi == null) {
            gonderimTarihi = LocalDateTime.now();
        }
    }
}
