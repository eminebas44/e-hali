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
public class KullaniciNavigasyonTest {

    private WebDriver driver;

    @BeforeEach
    void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage", "--window-size=1920,1080");
        driver = new ChromeDriver(options);
    }

    @Test
    void testNavigasyonAnasayfaDonus() {
        driver.get("http://localhost:5173/kategori/ipek");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement backBtn = wait.until(ExpectedConditions.elementToBeClickable(By.className("nav-back-btn")));
        backBtn.click();
        wait.until(ExpectedConditions.urlToBe("http://localhost:5173/"));
        Assertions.assertEquals("http://localhost:5173/", driver.getCurrentUrl());
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}