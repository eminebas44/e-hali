package org.example.ehali.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "satici")
public class Satici {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "satici_id")
    private Long saticiId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "kullanici_id", referencedColumnName = "id")
    private Kullanici kullanici;

    @Column(name = "ad", nullable = false, length = 100)
    private String ad;

    @Column(name = "soyad", nullable = false, length = 100)
    private String soyad;

    @Column(name = "telefon", length = 20)
    private String telefon;

    @Column(name = "kayit_tarihi")
    private LocalDate kayitTarihi;

    @PrePersist
    protected void onCreate() {
        if (kayitTarihi == null) {
            kayitTarihi = LocalDate.now();
        }
    }
}