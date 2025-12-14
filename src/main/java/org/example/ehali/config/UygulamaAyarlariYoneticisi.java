package org.example.ehali.config;

/**
 * Tembel (Lazy) Singleton Deseni Uygulaması
 * Uygulama genelinde tek bir yapılandırma yöneticisi örneği sağlar.
 */
public class UygulamaAyarlariYoneticisi {

    // 1. Singleton örneğini tutacak private static volatile değişken
    private static volatile UygulamaAyarlariYoneticisi instance;

    // Ayarlar
    private String uygulamaSurumu;
    private String yoneticiEpostasi;
    private String jwtSecretKey; // <-- Bunu yeni ekledik (Token üretirken lazım olacak)

    // 2. Private constructor (Dışarıdan erişimi engeller)
    private UygulamaAyarlariYoneticisi() {
        // Varsayılan ayarlar burada yükleniyor
        this.uygulamaSurumu = "1.0.0";
        this.yoneticiEpostasi = "admin@e-hali.com";
        this.jwtSecretKey = "cok-gizli-ve-guvenli-anahtar-12345"; // JWT imzalama anahtarı

        System.out.println("🚀 Config Yöneticisi Başlatıldı (Singleton Instance Created)");
    }

    // 3. Global Erişim Noktası (Thread-Safe)
    public static UygulamaAyarlariYoneticisi getInstance() {
        if (instance == null) { // İlk kontrol
            synchronized (UygulamaAyarlariYoneticisi.class) {
                if (instance == null) { // İkinci kontrol (Double-Check)
                    instance = new UygulamaAyarlariYoneticisi();
                }
            }
        }
        return instance;
    }

    // --- Getter ve Setter Metotları ---

    public String getUygulamaSurumu() {
        return uygulamaSurumu;
    }

    public String getYoneticiEpostasi() {
        return yoneticiEpostasi;
    }

    public String getJwtSecretKey() {
        return jwtSecretKey;
    }

    // Ayarları güncellemek gerekirse
    public void setYoneticiEpostasi(String yoneticiEpostasi) {
        this.yoneticiEpostasi = yoneticiEpostasi;
    }
}