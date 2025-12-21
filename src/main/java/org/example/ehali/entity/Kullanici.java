package org.example.ehali.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "kullanici") // Veritabanında tekil isim kullanıyoruz
public class Kullanici implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String ad;

    @Column(nullable = false, length = 100)
    private String soyad;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String sifre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol; // ADMIN, MUSTERI, SATICI

    // --- UserDetails Metodları (Spring Security İçin) ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Kullanıcının rolünü Spring Security'nin anlayacağı formata çeviriyoruz
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol.name()));
    }

    @Override
    public String getPassword() {
        return sifre;
    }

    @Override
    public String getUsername() {
        return email; // Giriş yaparken email kullanacağız
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}