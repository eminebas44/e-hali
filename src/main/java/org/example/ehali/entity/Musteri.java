package org.example.ehali.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "musteri")
public class Musteri {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "musteri_id")
    private Long musteriId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "kullanici_id", referencedColumnName = "id")
    private Kullanici kullanici;

    @Column(name = "ad", nullable = false, length = 100)
    private String ad;

    @Column(name = "soyad", nullable = false, length = 100)
    private String soyad;

    @Column(name = "adres", columnDefinition = "TEXT")
    private String adres;

    @CreationTimestamp
    @Column(name = "kayit_tarihi", updatable = false)
    private LocalDate kayitTarihi;
}
