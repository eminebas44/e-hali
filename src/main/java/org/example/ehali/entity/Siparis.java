package org.example.ehali.entity;

import com.fasterxml.jackson.annotation.JsonIgnore; // Kritik Import
import jakarta.persistence.*;
import org.example.ehali.entity.state.BeklemedeDurumu;
import org.example.ehali.entity.state.SiparisDurumu;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "siparis")
public class Siparis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "siparis_id")
    private Long siparisId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "musteri_id", nullable = false)
    private Musteri musteri;

    @Column(name = "siparis_tarihi")
    private LocalDate siparisTarihi;

    @Column(name = "toplam_tutar", precision = 10, scale = 2)
    private BigDecimal toplamTutar;

    @Column(name = "durum", length = 50)
    private String durumAdi;

    @JsonIgnore // JSON serileştirme hatasını engelleyen sihirli satır
    @Transient
    private SiparisDurumu durum;

    public Siparis() {
        this.durum = new BeklemedeDurumu();
        this.durumAdi = this.durum.getDurumAdi();
    }

    public void sonrakiDurum() {
        if (durum != null) {
            durum.sonrakiDurum(this);
            this.durumAdi = durum.getDurumAdi(); // Veritabanı adını güncelle
        }
    }

    public void iptalEt() {
        if (durum != null) {
            durum.iptalEt(this);
            this.durumAdi = durum.getDurumAdi(); // Veritabanı adını güncelle
        }
    }

    // --- Getter ve Setterlar ---

    public Long getSiparisId() {
        return siparisId;
    }

    public void setSiparisId(Long siparisId) {
        this.siparisId = siparisId;
    }

    public Musteri getMusteri() {
        return musteri;
    }

    public void setMusteri(Musteri musteri) {
        this.musteri = musteri;
    }

    public LocalDate getSiparisTarihi() {
        return siparisTarihi;
    }

    public void setSiparisTarihi(LocalDate siparisTarihi) {
        this.siparisTarihi = siparisTarihi;
    }

    public BigDecimal getToplamTutar() {
        return toplamTutar;
    }

    public void setToplamTutar(BigDecimal toplamTutar) {
        this.toplamTutar = toplamTutar;
    }

    public String getDurumAdi() {
        return durumAdi;
    }

    public void setDurumAdi(String durumAdi) {
        this.durumAdi = durumAdi;
    }

    public SiparisDurumu getDurum() {
        return durum;
    }

    public void setDurum(SiparisDurumu durum) {
        this.durum = durum;
        if (durum != null) {
            this.durumAdi = durum.getDurumAdi(); // Senkronizasyon
        }
    }

    @PrePersist
    protected void onCreate() {
        if (siparisTarihi == null) {
            siparisTarihi = LocalDate.now();
        }
        if (this.durumAdi == null && this.durum != null) {
            this.durumAdi = this.durum.getDurumAdi();
        }
    }
}