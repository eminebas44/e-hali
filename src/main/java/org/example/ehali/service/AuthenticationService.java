package org.example.ehali.service;

import lombok.RequiredArgsConstructor;
import org.example.ehali.dto.AuthenticationResponse;
import org.example.ehali.dto.AuthRequest;
import org.example.ehali.dto.RegisterRequest;
import org.example.ehali.entity.Kullanici;
import org.example.ehali.entity.Rol;
import org.example.ehali.entity.Satici;
import org.example.ehali.repository.KullaniciRepository;
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
    private final SaticiRepository saticiRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        // --- EKLEDİĞİMİZ LOG SATIRI ---
        // Hangi e-postanın geldiğini IntelliJ konsolundan takip edebilirsin.
        System.out.println("Kayıt denemesi yapılıyor. E-posta: " + request.getEmail());

        // 1. E-POSTA TEMİZLİĞİ VE KONTROLÜ
        String temizEmail = request.getEmail().toLowerCase().trim();

        if (repository.findByEmail(temizEmail).isPresent()) {
            throw new RuntimeException("Bu e-posta adresi (" + temizEmail + ") zaten kullanılıyor.");
        }

        // 2. ROL DÖNÜŞTÜRME
        Rol kullaniciRolu = Rol.USER;
        try {
            if (request.getRol() != null) {
                // request.getRol() objeyse toString() eklemek daha güvenlidir.
                kullaniciRolu = Rol.valueOf(request.getRol().toString().toUpperCase());
            }
        } catch (Exception e) {
            kullaniciRolu = Rol.USER;
        }

        // 3. KULLANICIYI KAYDET
        var user = Kullanici.builder()
                .ad(request.getAd())
                .soyad(request.getSoyad())
                .email(temizEmail)
                .sifre(passwordEncoder.encode(request.getSifre()))
                .rol(kullaniciRolu)
                .build();

        var savedUser = repository.save(user);

        // 4. SATICI KAYDI
        if (kullaniciRolu == Rol.SATICI) {
            Satici satici = new Satici();
            satici.setKullanici(savedUser);
            satici.setAd(request.getAd());
            satici.setSoyad(request.getSoyad());
            satici.setTelefon(request.getTelefon());
            saticiRepository.save(satici);
        }

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