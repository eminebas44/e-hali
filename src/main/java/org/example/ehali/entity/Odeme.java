package org.example.ehali.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "odeme")
public class Odeme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "odeme_id")
    private Long odemeId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "siparis_id", unique = true, nullable = false)
    private Siparis siparis;

    @Column(name = "odeme_tipi", length = 50)
    private String odemeTipi;

    @Column(name = "tutar", precision = 10, scale = 2)
    private BigDecimal tutar;

    @Column(name = "odeme_tarihi")
    private LocalDate odemeTarihi;

    public Odeme() {
    }

    public Long getOdemeId() {
        return odemeId;
    }

    public void setOdemeId(Long odemeId) {
        this.odemeId = odemeId;
    }

    public Siparis getSiparis() {
        return siparis;
    }

    public void setSiparis(Siparis siparis) {
        this.siparis = siparis;
    }

    public String getOdemeTipi() {
        return odemeTipi;
    }

    public void setOdemeTipi(String odemeTipi) {
        this.odemeTipi = odemeTipi;
    }

    public BigDecimal getTutar() {
        return tutar;
    }

    public void setTutar(BigDecimal tutar) {
        this.tutar = tutar;
    }

    public LocalDate getOdemeTarihi() {
        return odemeTarihi;
    }

    public void setOdemeTarihi(LocalDate odemeTarihi) {
        this.odemeTarihi = odemeTarihi;
    }

    @PrePersist
    protected void onCreate() {
        if (odemeTarihi == null) {
            odemeTarihi = LocalDate.now();
        }
    }
}
