package org.example.ehali.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "siparis_detay")
@Data
public class SiparisDetay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "siparis_id", nullable = false)
    private Siparis siparis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hali_id", nullable = false)
    private Hali hali;

    @Column(name = "adet", nullable = false)
    private int adet;

    @Column(name = "birim_fiyat", nullable = false)
    private BigDecimal birimFiyat;
}