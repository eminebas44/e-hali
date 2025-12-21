package org.example.ehali.service;

import org.example.ehali.dto.HaliDTO;
import org.example.ehali.dto.HaliGetirDTO;
import org.example.ehali.entity.Hali;
import org.example.ehali.entity.Kategori;
import org.example.ehali.entity.Satici;
import org.example.ehali.repository.HaliRepository;
import org.example.ehali.repository.KategoriRepository;
import org.example.ehali.repository.SaticiRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HaliService {

    private final HaliRepository haliRepository;
    private final KategoriRepository kategoriRepository;
    private final SaticiRepository saticiRepository;

    public HaliService(HaliRepository haliRepository,
                       KategoriRepository kategoriRepository,
                       SaticiRepository saticiRepository) {
        this.haliRepository = haliRepository;
        this.kategoriRepository = kategoriRepository;
        this.saticiRepository = saticiRepository;
    }

    // ✅ KATEGORİ + ALT KATEGORİLER (Composite Pattern Kullanımı)
    public List<HaliGetirDTO> findByKategoriAdi(String kategoriAdi) {

        // Repository'de tanımladığımız IgnoreCase metodunu çağırıyoruz
        Kategori anaKategori = kategoriRepository
                .findByKategoriAdiIgnoreCase(kategoriAdi)
                .orElseThrow(() -> new RuntimeException("Kategori bulunamadı: " + kategoriAdi));

        List<Kategori> tumKategoriler = new ArrayList<>();
        kategoriAgaciniTopla(anaKategori, tumKategoriler);

        return haliRepository.findByKategoriIn(tumKategoriler)
                .stream()
                .map(this::convertToGetirDTO)
                .collect(Collectors.toList());
    }

    // 🌳 Recursive Composite: Alt kategorileri toplar
    private void kategoriAgaciniTopla(Kategori kategori, List<Kategori> liste) {
        liste.add(kategori);
        if (kategori.getAltKategoriler() != null) {
            for (Kategori alt : kategori.getAltKategoriler()) {
                kategoriAgaciniTopla(alt, liste);
            }
        }
    }

    public List<HaliGetirDTO> findAllHalilarAsDTO() {
        return haliRepository.findAll()
                .stream()
                .map(this::convertToGetirDTO)
                .collect(Collectors.toList());
    }

    private HaliGetirDTO convertToGetirDTO(Hali hali) {
        HaliGetirDTO dto = new HaliGetirDTO();
        dto.setHaliId(hali.getHaliId());
        dto.setKategoriAdi(hali.getKategori().getKategoriAdi());
        dto.setTur(hali.getTur());
        dto.setMalzeme(hali.getMalzeme());
        dto.setFiyat(hali.getFiyat());
        dto.setEn(hali.getEn());
        dto.setBoy(hali.getBoy());
        dto.setUretimYili(hali.getUretimYili());
        dto.setUretimSekli(hali.getUretimSekli());
        dto.setMillet(hali.getMillet());
        dto.setStokAdedi(hali.getStokAdedi());
        return dto;
    }

    // --- CRUD ---
    @Transactional
    public Hali createHaliFromDTO(HaliDTO haliDTO) {
        Kategori kategori = kategoriRepository.findById(haliDTO.getKategoriId())
                .orElseThrow(() -> new RuntimeException("Kategori bulunamadı"));
        Satici satici = saticiRepository.findById(haliDTO.getSaticiId())
                .orElseThrow(() -> new RuntimeException("Satıcı bulunamadı"));

        Hali hali = new Hali.Builder()
                .kategori(kategori)
                .satici(satici)
                .tur(haliDTO.getTur())
                .malzeme(haliDTO.getMalzeme())
                .fiyat(haliDTO.getFiyat())
                .en(haliDTO.getEn())
                .boy(haliDTO.getBoy())
                .uretimYili(haliDTO.getUretimYili())
                .uretimSekli(haliDTO.getUretimSekli())
                .millet(haliDTO.getMillet())
                .stokAdedi(haliDTO.getStokAdedi())
                .build();

        return haliRepository.save(hali);
    }

    public Optional<Hali> findById(Long id) {
        return haliRepository.findById(id);
    }

    public void deleteById(Long id) {
        haliRepository.deleteById(id);
    }
}