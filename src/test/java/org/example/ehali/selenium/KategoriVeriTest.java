package org.example.ehali.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.Duration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class KategoriVeriTest {
    private WebDriver driver;

    @BeforeEach
    void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage", "--window-size=1920,1080");
        driver = new ChromeDriver(options);
    }

    @Test
    void testKategoriUrunListesiRender() {
        driver.get("http://localhost:5173/kategori/ipek");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        int urunSayisi = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("catalog-card"))).size();
        Assertions.assertTrue(urunSayisi > 0, "Kategori sayfasında ürünler listelenemedi!");
    }

    @AfterEach
    void tearDown() { if (driver != null) driver.quit(); }
}