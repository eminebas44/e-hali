package org.example.ehali.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String ad;
    private String soyad;
    private String email;
    private String sifre;

    // Frontend'den (React) gelecek rol bilgisi (MUSTERI veya SATICI)
    private Object rol;

    // Müşteriler için gerekli alan
    private String adres;

    // Satıcılar için gerekli alan
    private String telefon;
}