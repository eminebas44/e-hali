package org.example.ehali.guvenlik.dto;

public class GirisIstegi {
    private String email;
    private String sifre;

    public GirisIstegi() {
    }

    public GirisIstegi(String email, String sifre) {
        this.email = email;
        this.sifre = sifre;
    }

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
