package org.example.ehali.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AnasayfaLogoTest {
    private WebDriver driver;

    @BeforeEach
    void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage", "--window-size=1920,1080");
        driver = new ChromeDriver(options);
    }

    @Test
    void testNavbarLogoGorunurlugu() {
        driver.get("http://localhost:5173/");
        WebElement logo = driver.findElement(By.className("nav-logo"));
        Assertions.assertTrue(logo.isDisplayed(), "Ana sayfada logo görüntülenemiyor!");
    }

    @AfterEach
    void tearDown() { if (driver != null) driver.quit(); }
}