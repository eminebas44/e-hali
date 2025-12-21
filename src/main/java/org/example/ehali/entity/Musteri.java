package org.example.ehali.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDate;

@Getter
@Setter
@Builder
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

    @Column(name = "adres", columnDefinition = "TEXT")
    private String adres;

    @CreationTimestamp
    @Column(name = "kayit_tarihi", updatable = false)
    private LocalDate kayitTarihi;
}