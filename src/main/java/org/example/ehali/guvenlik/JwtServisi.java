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

    private static final String GIZLI_ANAHTAR = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    public String kullaniciAdiCikar(String token) {
        return claimCikar(token, Claims::getSubject);
    }

    public <T> T claimCikar(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = tumClaimleriCikar(token);
        return claimsResolver.apply(claims);
    }

    public boolean tokenGecerliMi(String token, UserDetails userDetails) {
        final String username = kullaniciAdiCikar(token);
        return (username.equals(userDetails.getUsername())) && !tokenSuresiDolmusMu(token);
    }

    private boolean tokenSuresiDolmusMu(String token) {
        return tokenBitisTarihiCikar(token).before(new Date());
    }

    private Date tokenBitisTarihiCikar(String token) {
        return claimCikar(token, Claims::getExpiration);
    }

    // --- DÜZELTİLEN KISIM BURASI (İsimler tokenOlustur yapıldı) ---

    public String tokenOlustur(UserDetails userDetails) {
        return tokenOlustur(new HashMap<>(), userDetails);
    }

    public String tokenOlustur(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 Saat
                .signWith(anahtarGetir(), SignatureAlgorithm.HS256)
                .compact();
    }
    // -------------------------------------------------------------

    private Claims tumClaimleriCikar(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(anahtarGetir())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key anahtarGetir() {
        byte[] keyBytes = Decoders.BASE64.decode(GIZLI_ANAHTAR);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}