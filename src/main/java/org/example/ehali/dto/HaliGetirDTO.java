package org.example.ehali.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class HaliGetirDTO {

    private Long haliId;
    private String kategoriAdi;
    private String saticiAdi;
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
    private LocalDate eklenmeTarihi;

    public HaliGetirDTO() {
    }

    public Long getHaliId() { return haliId; }
    public void setHaliId(Long haliId) { this.haliId = haliId; }

    public String getKategoriAdi() { return kategoriAdi; }
    public void setKategoriAdi(String kategoriAdi) { this.kategoriAdi = kategoriAdi; }

    public String getSaticiAdi() { return saticiAdi; }
    public void setSaticiAdi(String saticiAdi) { this.saticiAdi = saticiAdi; }

    public String getTur() { return tur; }
    public void setTur(String tur) { this.tur = tur; }

    public String getBirincilRenk() { return birincilRenk; }
    public void setBirincilRenk(String birincilRenk) { this.birincilRenk = birincilRenk; }

    public String getIkincilRenk() { return ikincilRenk; }
    public void setIkincilRenk(String ikincilRenk) { this.ikincilRenk = ikincilRenk; }

    public String getMillet() { return millet; }
    public void setMillet(String millet) { this.millet = millet; }

    public BigDecimal getEn() { return en; }
    public void setEn(BigDecimal en) { this.en = en; }

    public BigDecimal getBoy() { return boy; }
    public void setBoy(BigDecimal boy) { this.boy = boy; }

    public String getMalzeme() { return malzeme; }
    public void setMalzeme(String malzeme) { this.malzeme = malzeme; }

    public BigDecimal getFiyat() { return fiyat; }
    public void setFiyat(BigDecimal fiyat) { this.fiyat = fiyat; }

    public Integer getGarantiSuresi() { return garantiSuresi; }
    public void setGarantiSuresi(Integer garantiSuresi) { this.garantiSuresi = garantiSuresi; }

    public Integer getUretimYili() { return uretimYili; }
    public void setUretimYili(Integer uretimYili) { this.uretimYili = uretimYili; }

    public String getUretimSekli() { return uretimSekli; }
    public void setUretimSekli(String uretimSekli) { this.uretimSekli = uretimSekli; }

    public String getGenelCesit() { return genelCesit; }
    public void setGenelCesit(String genelCesit) { this.genelCesit = genelCesit; }

    public Integer getStokAdedi() { return stokAdedi; }
    public void setStokAdedi(Integer stokAdedi) { this.stokAdedi = stokAdedi; }

    public LocalDate getEklenmeTarihi() { return eklenmeTarihi; }
    public void setEklenmeTarihi(LocalDate eklenmeTarihi) { this.eklenmeTarihi = eklenmeTarihi; }
}
