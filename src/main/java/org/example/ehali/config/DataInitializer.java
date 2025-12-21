package org.example.ehali.config;

import lombok.RequiredArgsConstructor;
import org.example.ehali.entity.Hali;
import org.example.ehali.entity.Kategori;
import org.example.ehali.repository.HaliRepository;
import org.example.ehali.repository.KategoriRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final KategoriRepository kategoriRepository;
    private final HaliRepository haliRepository;

    @Override
    public void run(String... args) {
        List<String> kategoriIsimleri = Arrays.asList(
                "İpek", "Bünyan", "Yün", "Kaşmir", "Modern", "Antik", "Kilim", "Vintage"
        );

        kategoriIsimleri.forEach(ad -> {
            // findByKategoriAdiIgnoreCase kullanarak Repository ile tam uyum sağlıyoruz
            if (kategoriRepository.findByKategoriAdiIgnoreCase(ad).isEmpty()) {
                Kategori yeniKat = kategoriRepository.save(new Kategori(ad));

                // BigDecimal dönüşümleri yapılarak Builder hatası çözüldü
                Hali ornekHali = new Hali.Builder()
                        .kategori(yeniKat)
                        .tur(ad + " Koleksiyonu Özel Parça")
                        .fiyat(new BigDecimal("15000.00"))
                        .malzeme(ad + " ipliği")
                        .stokAdedi(10)
                        .en(new BigDecimal("120"))
                        .boy(new BigDecimal("180"))
                        .uretimSekli("El Dokuması")
                        .build();

                haliRepository.save(ornekHali);
            }
        });
        System.out.println("Sistem hazır: Kategoriler ve Örnek Halılar yüklendi.");
    }
}