package org.example.ehali.service;

import org.example.ehali.entity.*;
import org.example.ehali.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class SepetService {

    private final SepetRepository sepetRepository;
    private final HaliRepository haliRepository;
    private final MusteriRepository musteriRepository;
    private final SiparisRepository siparisRepository;
    private final SiparisDetayRepository siparisDetayRepository;

    public SepetService(SepetRepository sepetRepository,
                        HaliRepository haliRepository,
                        MusteriRepository musteriRepository,
                        SiparisRepository siparisRepository,
                        SiparisDetayRepository siparisDetayRepository) {
        this.sepetRepository = sepetRepository;
        this.haliRepository = haliRepository;
        this.musteriRepository = musteriRepository;
        this.siparisRepository = siparisRepository;
        this.siparisDetayRepository = siparisDetayRepository;
    }

    public Sepet getSepetByMusteri(Long musteriId) {
        return sepetRepository.findByMusteri_MusteriId(musteriId)
                .orElseGet(() -> {
                    Musteri musteri = musteriRepository.findById(musteriId)
                            .orElseThrow(() -> new RuntimeException("Müşteri bulunamadı"));
                    Sepet yeniSepet = new Sepet();
                    yeniSepet.setMusteri(musteri);
                    return sepetRepository.save(yeniSepet);
                });
    }

    @Transactional
    public void sepeteEkle(Long musteriId, Long haliId, int adet) {
        Sepet sepet = getSepetByMusteri(musteriId);
        Hali hali = haliRepository.findById(haliId)
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı"));

        if (hali.getStokAdedi() < adet) {
            throw new RuntimeException("Yetersiz Stok! Mevcut stok: " + hali.getStokAdedi());
        }

        Optional<SepetDetay> mevcutDetay = sepet.getSepetDetaylari().stream()
                .filter(d -> d.getHali().getHaliId().equals(haliId))
                .findFirst();

        if (mevcutDetay.isPresent()) {
            SepetDetay detay = mevcutDetay.get();
            int yeniAdet = detay.getAdet() + adet;
            if (hali.getStokAdedi() < yeniAdet) {
                throw new RuntimeException("Stok yetersiz! Toplam istenen: " + yeniAdet);
            }
            detay.setAdet(yeniAdet);
        } else {
            SepetDetay yeniDetay = new SepetDetay();
            yeniDetay.setSepet(sepet);
            yeniDetay.setHali(hali);
            yeniDetay.setAdet(adet);
            sepet.getSepetDetaylari().add(yeniDetay);
        }
        sepetRepository.save(sepet);
    }

    @Transactional
    public void sepettenCikar(Long musteriId, Long haliId) {
        Sepet sepet = getSepetByMusteri(musteriId);
        sepet.getSepetDetaylari().removeIf(d -> d.getHali().getHaliId().equals(haliId));
        sepetRepository.save(sepet);
    }

    @Transactional
    public void sepetiBosalt(Long musteriId) {
        Sepet sepet = getSepetByMusteri(musteriId);
        sepet.getSepetDetaylari().clear();
        sepetRepository.save(sepet);
    }

    @Transactional
    public Long sepetiOnaylaVeSiparisVer(Long musteriId) {
        Sepet sepet = getSepetByMusteri(musteriId);

        if (sepet.getSepetDetaylari().isEmpty()) {
            throw new RuntimeException("Sepetiniz boş, sipariş oluşturulamaz.");
        }

        for (SepetDetay detay : sepet.getSepetDetaylari()) {
            Hali hali = detay.getHali();
            if (hali.getStokAdedi() < detay.getAdet()) {
                throw new RuntimeException(hali.getTur() + " ürünü için stok yetersiz.");
            }
            hali.setStokAdedi(hali.getStokAdedi() - detay.getAdet());
            haliRepository.save(hali);
        }

        Siparis siparis = new Siparis();
        siparis.setMusteri(sepet.getMusteri());
        siparis.setToplamTutar(sepet.getToplamTutar());

        Siparis kaydedilenSiparis = siparisRepository.save(siparis);

        for (SepetDetay sepetDetay : sepet.getSepetDetaylari()) {
            SiparisDetay siparisDetay = new SiparisDetay();
            siparisDetay.setSiparis(kaydedilenSiparis);
            siparisDetay.setHali(sepetDetay.getHali());
            siparisDetay.setAdet(sepetDetay.getAdet());
            siparisDetay.setBirimFiyat(sepetDetay.getHali().getFiyat());
            siparisDetayRepository.save(siparisDetay);
        }

        sepet.getSepetDetaylari().clear();
        sepetRepository.save(sepet);

        return kaydedilenSiparis.getSiparisId();
    }
}