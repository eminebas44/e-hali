package org.example.ehali.service;

import lombok.RequiredArgsConstructor;
import org.example.ehali.dto.AuthenticationResponse;
import org.example.ehali.dto.AuthRequest;
import org.example.ehali.dto.RegisterRequest;
import org.example.ehali.entity.Kullanici;
import org.example.ehali.entity.Musteri; // Yeni eklendi
import org.example.ehali.entity.Rol;
import org.example.ehali.entity.Satici;
import org.example.ehali.repository.KullaniciRepository;
import org.example.ehali.repository.MusteriRepository; // Yeni eklendi
import org.example.ehali.repository.SaticiRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final KullaniciRepository repository;
    private final MusteriRepository musteriRepository; // Müşteri tablosu için eklendi
    private final SaticiRepository saticiRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        System.out.println("Kayıt işlemi başlatıldı: " + request.getEmail() + " Rol: " + request.getRol());

        // 1. E-POSTA KONTROLÜ
        String temizEmail = request.getEmail().toLowerCase().trim();
        if (repository.findByEmail(temizEmail).isPresent()) {
            throw new RuntimeException("Bu e-posta adresi zaten kayıtlı!");
        }

        // 2. ROL BELİRLEME (Varsayılan olarak MUSTERI yapıyoruz)
        Rol kullaniciRolu;
        try {
            kullaniciRolu = Rol.valueOf(request.getRol().toString().toUpperCase());
        } catch (Exception e) {
            kullaniciRolu = Rol.MUSTERI;
        }

        // 3. ORTAK KULLANICI NESNESİNİ OLUŞTUR
        // Ad ve Soyad artık burada tutuluyor!
        Kullanici user = Kullanici.builder()
                .ad(request.getAd())
                .soyad(request.getSoyad())
                .email(temizEmail)
                .sifre(passwordEncoder.encode(request.getSifre()))
                .rol(kullaniciRolu)
                .build();

        // 4. ROLE GÖRE ÖZEL TABLOYA KAYIT
        if (kullaniciRolu == Rol.MUSTERI) {
            Musteri musteri = Musteri.builder()
                    .kullanici(user) // User nesnesini içine gömüyoruz (Cascade sayesinde user da kaydolur)
                    .adres(request.getAdres())
                    .build();
            musteriRepository.save(musteri);

        } else if (kullaniciRolu == Rol.SATICI) {
            Satici satici = Satici.builder()
                    .kullanici(user)
                    .telefon(request.getTelefon())
                    .build();
            saticiRepository.save(satici);

        } else {
            // Admin ise sadece kullanıcı tablosuna kaydet
            repository.save(user);
        }

        // 5. TOKEN OLUŞTUR VE DÖN
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}