package org.example.ehali.guvenlik.dto;

public class KimlikDogrulamaYaniti {
    private String token;

    public KimlikDogrulamaYaniti() {
    }

    public KimlikDogrulamaYaniti(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
