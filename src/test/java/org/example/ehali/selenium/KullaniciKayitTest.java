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
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class KullaniciKayitTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Test
    void testKayit() {
        driver.get("http://localhost:5173/kayit-ol");
        WebElement ad = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ad")));
        ad.sendKeys("JenkinsTest");
        driver.findElement(By.name("soyad")).sendKeys("User");
        driver.findElement(By.name("email")).sendKeys("jenkins" + System.currentTimeMillis() + "@test.com");
        driver.findElement(By.name("adres")).sendKeys("Jenkins Sanal Ofis");
        driver.findElement(By.name("sifre")).sendKeys("123456");
        driver.findElement(By.className("submit-btn-premium")).click();
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
        wait.until(ExpectedConditions.urlContains("/giris"));
        assertTrue(driver.getCurrentUrl().contains("/giris"));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }
}