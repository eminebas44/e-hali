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
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class KategoriFiltrelemeTest {

    private WebDriver driver;

    @BeforeEach
    void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage", "--window-size=1920,1080");
        driver = new ChromeDriver(options);
    }

    @Test
    void testKategoriFiltrelemeModern() {
        driver.get("http://localhost:5173/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement modernBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@class, 'cat-btn') and text()='Modern']")));
        modernBtn.click();

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("compact-product-card")));

        List<WebElement> categoryTags = driver.findElements(By.className("hali-kat-tag"));

        Assertions.assertFalse(categoryTags.isEmpty(), "Filtreleme sonrası hiçbir ürün bulunamadı!");

        for (WebElement tag : categoryTags) {
            String tagText = tag.getText();
            Assertions.assertTrue(tagText.equalsIgnoreCase("Modern Koleksiyonu"),
                    "Hatalı kategori etiketi bulundu: " + tagText);
        }
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}