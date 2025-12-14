package org.example.ehali.guvenlik;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServisi {

    // 256-bit (32-byte) veya daha uzun bir anahtar olmalı.
    private static final String GIZLI_ANAHTAR = "4D6251655468576D5A7134743777217A25432A462D4A614E645267556B587032";

    public String kullaniciAdiCikar(String token) {
        return claimCikar(token, Claims::getSubject);
    }

    public <T> T claimCikar(String token, Function<Claims, T> claimsCozumleyici) {
        final Claims claims = tumClaimleriCikar(token);
        return claimsCozumleyici.apply(claims);
    }

    public String tokenOlustur(UserDetails kullaniciDetaylari) {
        return tokenOlustur(new HashMap<>(), kullaniciDetaylari);
    }

    public String tokenOlustur(Map<String, Object> ekstraClaims, UserDetails kullaniciDetaylari) {
        return Jwts
                .builder()
                .setClaims(ekstraClaims)
                .setSubject(kullaniciDetaylari.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 saat geçerli
                .signWith(getImzaAnahtari(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean tokenGecerliMi(String token, UserDetails kullaniciDetaylari) {
        final String kullaniciAdi = kullaniciAdiCikar(token);
        return (kullaniciAdi.equals(kullaniciDetaylari.getUsername())) && !tokenSuresiGecmisMi(token);
    }

    private boolean tokenSuresiGecmisMi(String token) {
        return sonKullanmaTarihiCikar(token).before(new Date());
    }

    private Date sonKullanmaTarihiCikar(String token) {
        return claimCikar(token, Claims::getExpiration);
    }

    private Claims tumClaimleriCikar(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getImzaAnahtari())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getImzaAnahtari() {
        byte[] anahtarBytes = Decoders.BASE64.decode(GIZLI_ANAHTAR);
        return Keys.hmacShaKeyFor(anahtarBytes);
    }
}
