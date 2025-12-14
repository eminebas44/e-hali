package org.example.ehali.guvenlik;

import org.example.ehali.entity.Kullanici;
import org.example.ehali.entity.Rol;
import org.example.ehali.guvenlik.dto.GirisIstegi;
import org.example.ehali.guvenlik.dto.KayitIstegi;
import org.example.ehali.guvenlik.dto.KimlikDogrulamaYaniti;
import org.example.ehali.repository.KullaniciRepository;
import org.springframework.context.annotation.Lazy; // Lazy importu eklendi
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class KimlikDogrulamaServisi {

    private final KullaniciRepository kullaniciRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServisi jwtServisi;
    private final AuthenticationManager authenticationManager;

    // @Lazy ekledik ki "Döngüsel Bağımlılık" hatası almayasın
    public KimlikDogrulamaServisi(KullaniciRepository kullaniciRepository,
                                  PasswordEncoder passwordEncoder,
                                  JwtServisi jwtServisi,
                                  @Lazy AuthenticationManager authenticationManager) {
        this.kullaniciRepository = kullaniciRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtServisi = jwtServisi;
        this.authenticationManager = authenticationManager;
    }

    public KimlikDogrulamaYaniti kayit(KayitIstegi istek) {
        // 1. ROL KONTROLÜ (SİSTEM ÇÖKMESİN DİYE)
        Rol kullaniciRolu;
        try {
            // Gelen veriyi (örn: "admin") büyük harfe çevirip Enum'a dönüştür
            kullaniciRolu = Rol.valueOf(istek.getRol().toUpperCase());
        } catch (Exception e) {
            // Eğer "a", "b" gibi geçersiz bir şey gelirse varsayılan USER yap
            kullaniciRolu = Rol.USER;
        }

        // 2. KULLANICIYI OLUŞTUR (Builder Kullanarak)
        // Not: Ad ve Soyad alanlarını da ekledik.
        Kullanici kullanici = Kullanici.builder()
                .ad(istek.getAd())
                .soyad(istek.getSoyad())
                .email(istek.getEmail())
                .sifre(passwordEncoder.encode(istek.getSifre()))
                .rol(kullaniciRolu)
                .build();

        kullaniciRepository.save(kullanici);

        String jwtToken = jwtServisi.tokenOlustur(kullanici);
        return new KimlikDogrulamaYaniti(jwtToken);
    }

    public KimlikDogrulamaYaniti giris(GirisIstegi istek) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        istek.getEmail(),
                        istek.getSifre()
                )
        );
        Kullanici kullanici = kullaniciRepository.findByEmail(istek.getEmail())
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        String jwtToken = jwtServisi.tokenOlustur(kullanici);
        return new KimlikDogrulamaYaniti(jwtToken);
    }
}