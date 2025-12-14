package org.example.ehali.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "hali")
public class Hali implements HaliArayuz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hali_id")
    private Long haliId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kategori_id")
    private Kategori kategori;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "satici_id")
    private Satici satici;

    @Column(name = "tur", length = 100)
    private String tur;

    @Column(name = "birincil_renk", length = 50)
    private String birincilRenk;

    @Column(name = "ikincil_renk", length = 50)
    private String ikincilRenk;

    @Column(name = "millet", length = 50)
    private String millet;

    @Column(name = "en", precision = 5, scale = 2)
    private BigDecimal en;

    @Column(name = "boy", precision = 5, scale = 2)
    private BigDecimal boy;

    @Column(name = "malzeme", length = 50)
    private String malzeme;

    @Column(name = "fiyat", precision = 10, scale = 2)
    private BigDecimal fiyat;

    @Column(name = "garanti_suresi")
    private Integer garantiSuresi;

    @Column(name = "uretim_yili")
    private Integer uretimYili;

    @Column(name = "uretim_sekli", length = 50)
    private String uretimSekli;

    @Column(name = "genel_cesit", length = 100)
    private String genelCesit;

    @Column(name = "stok_adedi")
    private Integer stokAdedi;

    @Column(name = "eklenme_tarihi")
    private LocalDate eklenmeTarihi;

    public Hali() {
    }

    private Hali(Builder builder) {
        this.kategori = builder.kategori;
        this.satici = builder.satici;
        this.tur = builder.tur;
        this.birincilRenk = builder.birincilRenk;
        this.ikincilRenk = builder.ikincilRenk;
        this.millet = builder.millet;
        this.en = builder.en;
        this.boy = builder.boy;
        this.malzeme = builder.malzeme;
        this.fiyat = builder.fiyat;
        this.garantiSuresi = builder.garantiSuresi;
        this.uretimYili = builder.uretimYili;
        this.uretimSekli = builder.uretimSekli;
        this.genelCesit = builder.genelCesit;
        this.stokAdedi = builder.stokAdedi;
    }

    @Override
    public BigDecimal getFiyat() {
        return this.fiyat;
    }

    @Override
    public String getAciklama() {
        return this.tur;
    }

    public Long getHaliId() { return haliId; }
    public Kategori getKategori() { return kategori; }
    public Satici getSatici() { return satici; }
    public String getTur() { return tur; }
    public String getBirincilRenk() { return birincilRenk; }
    public String getIkincilRenk() { return ikincilRenk; }
    public String getMillet() { return millet; }
    public BigDecimal getEn() { return en; }
    public BigDecimal getBoy() { return boy; }
    public String getMalzeme() { return malzeme; }
    public Integer getGarantiSuresi() { return garantiSuresi; }
    public Integer getUretimYili() { return uretimYili; }
    public String getUretimSekli() { return uretimSekli; }
    public String getGenelCesit() { return genelCesit; }
    public Integer getStokAdedi() { return stokAdedi; }
    public LocalDate getEklenmeTarihi() { return eklenmeTarihi; }
    
    public void setHaliId(Long haliId) { this.haliId = haliId; }
    public void setKategori(Kategori kategori) { this.kategori = kategori; }
    public void setSatici(Satici satici) { this.satici = satici; }
    public void setTur(String tur) { this.tur = tur; }
    public void setBirincilRenk(String birincilRenk) { this.birincilRenk = birincilRenk; }
    public void setIkincilRenk(String ikincilRenk) { this.ikincilRenk = ikincilRenk; }
    public void setMillet(String millet) { this.millet = millet; }
    public void setEn(BigDecimal en) { this.en = en; }
    public void setBoy(BigDecimal boy) { this.boy = boy; }
    public void setMalzeme(String malzeme) { this.malzeme = malzeme; }
    public void setFiyat(BigDecimal fiyat) { this.fiyat = fiyat; }
    public void setGarantiSuresi(Integer garantiSuresi) { this.garantiSuresi = garantiSuresi; }
    public void setUretimYili(Integer uretimYili) { this.uretimYili = uretimYili; }
    public void setUretimSekli(String uretimSekli) { this.uretimSekli = uretimSekli; }
    public void setGenelCesit(String genelCesit) { this.genelCesit = genelCesit; }
    public void setStokAdedi(Integer stokAdedi) { this.stokAdedi = stokAdedi; }
    public void setEklenmeTarihi(LocalDate eklenmeTarihi) { this.eklenmeTarihi = eklenmeTarihi; }

    @PrePersist
    protected void onCreate() {
        if (eklenmeTarihi == null) {
            eklenmeTarihi = LocalDate.now();
        }
    }

    public static class Builder {
        private Kategori kategori;
        private Satici satici;
        private String tur;
        private String birincilRenk;
        private String ikincilRenk;
        private String millet;
        private BigDecimal en;
        private BigDecimal boy;
        private String malzeme;
        private BigDecimal fiyat;
        private Integer garantiSuresi;
        private Integer uretimYili;
        private String uretimSekli;
        private String genelCesit;
        private Integer stokAdedi;

        public Builder kategori(Kategori kategori) { this.kategori = kategori; return this; }
        public Builder satici(Satici satici) { this.satici = satici; return this; }
        public Builder tur(String tur) { this.tur = tur; return this; }
        public Builder birincilRenk(String birincilRenk) { this.birincilRenk = birincilRenk; return this; }
        public Builder ikincilRenk(String ikincilRenk) { this.ikincilRenk = ikincilRenk; return this; }
        public Builder millet(String millet) { this.millet = millet; return this; }
        public Builder en(BigDecimal en) { this.en = en; return this; }
        public Builder boy(BigDecimal boy) { this.boy = boy; return this; }
        public Builder malzeme(String malzeme) { this.malzeme = malzeme; return this; }
        public Builder fiyat(BigDecimal fiyat) { this.fiyat = fiyat; return this; }
        public Builder garantiSuresi(Integer garantiSuresi) { this.garantiSuresi = garantiSuresi; return this; }
        public Builder uretimYili(Integer uretimYili) { this.uretimYili = uretimYili; return this; }
        public Builder uretimSekli(String uretimSekli) { this.uretimSekli = uretimSekli; return this; }
        public Builder genelCesit(String genelCesit) { this.genelCesit = genelCesit; return this; }
        public Builder stokAdedi(Integer stokAdedi) { this.stokAdedi = stokAdedi; return this; }

        public Hali build() {
            return new Hali(this);
        }
    }
}
