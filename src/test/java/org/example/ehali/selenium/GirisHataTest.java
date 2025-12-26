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
public class GirisHataTest {
    private WebDriver driver;

    @BeforeEach
    void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage", "--window-size=1920,1080");
        driver = new ChromeDriver(options);
    }

    @Test
    void testYanlisGirisHatasi() {
        driver.get("http://localhost:5173/giris");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='email']"))).sendKeys("hata@ehali.com");
        driver.findElement(By.cssSelector("input[type='password']")).sendKeys("111111");
        driver.findElement(By.className("btn-giris")).click();
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("error-msg")));
        Assertions.assertTrue(error.getText().contains("hatalı"));
    }

    @AfterEach
    void tearDown() { if (driver != null) driver.quit(); }
}