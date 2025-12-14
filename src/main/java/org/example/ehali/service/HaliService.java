package org.example.ehali.service;

import org.example.ehali.dto.HaliDTO;
import org.example.ehali.dto.HaliGetirDTO;
import org.example.ehali.entity.Hali;
import org.example.ehali.entity.Kategori;
import org.example.ehali.entity.Satici;
import org.example.ehali.repository.HaliRepository;
import org.example.ehali.repository.KategoriRepository;
import org.example.ehali.repository.SaticiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HaliService {

    private final HaliRepository haliRepository;
    private final KategoriRepository kategoriRepository;
    private final SaticiRepository saticiRepository;

    @Autowired
    public HaliService(HaliRepository haliRepository, KategoriRepository kategoriRepository, SaticiRepository saticiRepository) {
        this.haliRepository = haliRepository;
        this.kategoriRepository = kategoriRepository;
        this.saticiRepository = saticiRepository;
    }

    public List<HaliGetirDTO> findAllHalilarAsDTO() {
        return haliRepository.findAll().stream()
                .map(this::convertToGetirDTO)
                .collect(Collectors.toList());
    }

    private HaliGetirDTO convertToGetirDTO(Hali hali) {
        HaliGetirDTO dto = new HaliGetirDTO();
        dto.setHaliId(hali.getHaliId());
        dto.setKategoriAdi(hali.getKategori() != null ? hali.getKategori().getKategoriAdi() : null);
        dto.setSaticiAdi(hali.getSatici() != null ? hali.getSatici().getAd() + " " + hali.getSatici().getSoyad() : null);
        dto.setTur(hali.getTur());
        dto.setBirincilRenk(hali.getBirincilRenk());
        dto.setIkincilRenk(hali.getIkincilRenk());
        dto.setMillet(hali.getMillet());
        dto.setEn(hali.getEn());
        dto.setBoy(hali.getBoy());
        dto.setMalzeme(hali.getMalzeme());
        dto.setFiyat(hali.getFiyat());
        dto.setGarantiSuresi(hali.getGarantiSuresi());
        dto.setUretimYili(hali.getUretimYili());
        dto.setUretimSekli(hali.getUretimSekli());
        dto.setGenelCesit(hali.getGenelCesit());
        dto.setStokAdedi(hali.getStokAdedi());
        dto.setEklenmeTarihi(hali.getEklenmeTarihi());
        return dto;
    }

    @Transactional
    public Hali createHaliFromDTO(HaliDTO haliDTO) {
        Kategori kategori = kategoriRepository.findById(haliDTO.getKategoriId())
                .orElseThrow(() -> new RuntimeException("Kategori not found with id: " + haliDTO.getKategoriId()));
        Satici satici = saticiRepository.findById(haliDTO.getSaticiId())
                .orElseThrow(() -> new RuntimeException("Satıcı not found with id: " + haliDTO.getSaticiId()));

        Hali yeniHali = new Hali.Builder()
                .kategori(kategori)
                .satici(satici)
                .tur(haliDTO.getTur())
                .birincilRenk(haliDTO.getBirincilRenk())
                .ikincilRenk(haliDTO.getIkincilRenk())
                .millet(haliDTO.getMillet())
                .en(haliDTO.getEn())
                .boy(haliDTO.getBoy())
                .malzeme(haliDTO.getMalzeme())
                .fiyat(haliDTO.getFiyat())
                .garantiSuresi(haliDTO.getGarantiSuresi())
                .uretimYili(haliDTO.getUretimYili())
                .uretimSekli(haliDTO.getUretimSekli())
                .genelCesit(haliDTO.getGenelCesit())
                .stokAdedi(haliDTO.getStokAdedi())
                .build();

        return haliRepository.save(yeniHali);
    }

    @Transactional
    public Hali updateHaliFromDTO(Long haliId, HaliDTO haliDTO) {
        Hali mevcutHali = haliRepository.findById(haliId)
                .orElseThrow(() -> new RuntimeException("Halı bulunamadı: " + haliId));

        Kategori kategori = kategoriRepository.findById(haliDTO.getKategoriId())
                .orElseThrow(() -> new RuntimeException("Kategori not found with id: " + haliDTO.getKategoriId()));
        Satici satici = saticiRepository.findById(haliDTO.getSaticiId())
                .orElseThrow(() -> new RuntimeException("Satıcı not found with id: " + haliDTO.getSaticiId()));

        mevcutHali.setKategori(kategori);
        mevcutHali.setSatici(satici);
        mevcutHali.setTur(haliDTO.getTur());
        mevcutHali.setBirincilRenk(haliDTO.getBirincilRenk());
        mevcutHali.setIkincilRenk(haliDTO.getIkincilRenk());
        mevcutHali.setMillet(haliDTO.getMillet());
        mevcutHali.setEn(haliDTO.getEn());
        mevcutHali.setBoy(haliDTO.getBoy());
        mevcutHali.setMalzeme(haliDTO.getMalzeme());
        mevcutHali.setFiyat(haliDTO.getFiyat());
        mevcutHali.setGarantiSuresi(haliDTO.getGarantiSuresi());
        mevcutHali.setUretimYili(haliDTO.getUretimYili());
        mevcutHali.setUretimSekli(haliDTO.getUretimSekli());
        mevcutHali.setGenelCesit(haliDTO.getGenelCesit());
        mevcutHali.setStokAdedi(haliDTO.getStokAdedi());

        return haliRepository.save(mevcutHali);
    }

    public Optional<Hali> findById(Long id) {
        return haliRepository.findById(id);
    }

    public void deleteById(Long id) {
        haliRepository.deleteById(id);
    }
}
