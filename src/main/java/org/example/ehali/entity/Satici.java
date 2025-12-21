package org.example.ehali.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
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

    @Column(name = "telefon", length = 20)
    private String telefon;

    @CreationTimestamp
    @Column(name = "kayit_tarihi", updatable = false)
    private LocalDate kayitTarihi;
}