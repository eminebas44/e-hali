package org.example.ehali.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "indirim")
public class Indirim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "indirim_id")
    private Long indirimId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hali_id", nullable = false)
    private Hali hali;

    @Column(name = "deger", precision = 10, scale = 2) // indirim_orani yerine daha genel bir 'deger'
    private BigDecimal deger;

    @Column(name = "strateji_tipi", length = 50) // Hangi indirim stratejisinin kullanılacağını belirtir
    private String stratejiTipi;

    @Column(name = "baslangic_tarihi")
    private LocalDate baslangicTarihi;

    @Column(name = "bitis_tarihi")
    private LocalDate bitisTarihi;

    public Indirim() {
    }

    public Long getIndirimId() {
        return indirimId;
    }

    public void setIndirimId(Long indirimId) {
        this.indirimId = indirimId;
    }

    public Hali getHali() {
        return hali;
    }

    public void setHali(Hali hali) {
        this.hali = hali;
    }

    public BigDecimal getDeger() {
        return deger;
    }

    public void setDeger(BigDecimal deger) {
        this.deger = deger;
    }

    public String getStratejiTipi() {
        return stratejiTipi;
    }

    public void setStratejiTipi(String stratejiTipi) {
        this.stratejiTipi = stratejiTipi;
    }

    public LocalDate getBaslangicTarihi() {
        return baslangicTarihi;
    }

    public void setBaslangicTarihi(LocalDate baslangicTarihi) {
        this.baslangicTarihi = baslangicTarihi;
    }

    public LocalDate getBitisTarihi() {
        return bitisTarihi;
    }

    public void setBitisTarihi(LocalDate bitisTarihi) {
        this.bitisTarihi = bitisTarihi;
    }
}
