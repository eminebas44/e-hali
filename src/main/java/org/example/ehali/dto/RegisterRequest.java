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
    private String rol;    // String olarak alıp Service içinde Enum'a çevirmek en güvenlisidir
    private String telefon; // Satici kaydı için gerekli alan eklendi
}