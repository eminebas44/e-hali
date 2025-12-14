package org.example.ehali.guvenlik.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KayitIstegi {

    @NotBlank(message = "Ad alanı boş bırakılamaz")
    private String ad;

    @NotBlank(message = "Soyad alanı boş bırakılamaz")
    private String soyad;

    @NotBlank(message = "E-posta alanı boş olamaz")
    @Email(message = "Lütfen geçerli bir e-posta adresi giriniz")
    private String email;

    @NotBlank(message = "Şifre boş olamaz")
    @Size(min = 6, message = "Şifre en az 6 karakter olmalıdır")
    private String sifre;

    private String rol;
}