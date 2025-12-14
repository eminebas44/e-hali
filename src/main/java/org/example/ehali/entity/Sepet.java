package org.example.ehali.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Sepet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "musteri_id")
    private Musteri musteri;

    @OneToMany(mappedBy = "sepet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SepetDetay> sepetDetaylari = new ArrayList<>();

    public BigDecimal getToplamTutar() {
        return sepetDetaylari.stream()
                .map(detay -> {
                    BigDecimal fiyat = detay.getHali().getFiyat();
                    BigDecimal adet = BigDecimal.valueOf(detay.getAdet());
                    return fiyat.multiply(adet);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}