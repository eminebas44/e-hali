package org.example.ehali.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder // <-- İŞTE BU EKSİKTİ, O YÜZDEN HATA ALDIN
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "kullanici")
public class Kullanici implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ad;      // Yeni eklediğimiz alan
    private String soyad;   // Yeni eklediğimiz alan

    @Column(unique = true) // Aynı mail ile 2 kişi kayıt olamasın
    private String email;

    private String sifre;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    // --- UserDetails Metodları (Spring Security İçin) ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rol.name()));
    }

    @Override
    public String getPassword() {
        return sifre;
    }

    @Override
    public String getUsername() {
        return email;
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