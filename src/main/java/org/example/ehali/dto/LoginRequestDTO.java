package org.example.ehali.dto;

public class LoginRequestDTO {
    private String email;
    private String sifre;

    // Getter ve Setter'lar
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }
}