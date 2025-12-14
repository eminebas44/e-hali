package org.example.ehali.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class SepetDetay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sepet_id")
    private Sepet sepet;

    @ManyToOne
    @JoinColumn(name = "hali_id")
    private Hali hali;

    private int adet;
}