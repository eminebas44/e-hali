package org.example.ehali.config;

/**
 * Tembel (Lazy) Singleton Deseni Uygulaması
 * Uygulama genelinde tek bir yapılandırma yöneticisi örneği sağlar.
 */
public class UygulamaAyarlariYoneticisi {

    // 1. Singleton örneğini tutacak private static değişken
    // 'volatile' anahtar kelimesi, çoklu iş parçacığı ortamında
    // 'instance' değişkeninin her zaman ana bellekten okunmasını sağlar.
    private static volatile UygulamaAyarlariYoneticisi instance;

    // Örnek bir yapılandırma özelliği
    private String uygulamaSurumu;
    private String yoneticiEpostasi;

    // 2. Dışarıdan nesne oluşturulmasını engellemek için private constructor
    private UygulamaAyarlariYoneticisi() {
        // Gerçek bir uygulamada, bu constructor içinde
        // yapılandırma dosyalarından (örn: application.properties) ayarlar okunabilir.
        this.uygulamaSurumu = "1.0.0";
        this.yoneticiEpostasi = "admin@e-hali.com";
        System.out.println("UygulamaAyarlariYoneticisi örneği oluşturuldu (Tembel Singleton).");
    }

    // 3. Singleton örneğine erişim için public static metot
    // Double-Checked Locking (Çift Kontrollü Kilitleme) ile tembel başlatma ve iş parçacığı güvenliği sağlanır.
    public static UygulamaAyarlariYoneticisi getInstance() {
        if (instance == null) { // İlk kontrol: Performans için (kilitlenmeden önce)
            synchronized (UygulamaAyarlariYoneticisi.class) { // Senkronize blok: İş parçacığı güvenliği için
                if (instance == null) { // İkinci kontrol: Nesnenin bir kez oluşturulduğundan emin olmak için
                    instance = new UygulamaAyarlariYoneticisi();
                }
            }
        }
        return instance;
    }

    // Yapılandırma özelliklerine erişim metotları
    public String getUygulamaSurumu() {
        return uygulamaSurumu;
    }

    public String getYoneticiEpostasi() {
        return yoneticiEpostasi;
    }

    // Ayarları güncellemek için (isteğe bağlı, Singleton'ın doğasına göre dikkatli kullanılmalı)
    public void setUygulamaSurumu(String uygulamaSurumu) {
        this.uygulamaSurumu = uygulamaSurumu;
    }

    public void setYoneticiEpostasi(String yoneticiEpostasi) {
        this.yoneticiEpostasi = yoneticiEpostasi;
    }
}
