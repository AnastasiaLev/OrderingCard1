package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CallbackTest {
    private WebDriver driver;


    @BeforeEach
    void setUp() { driver = new ChromeDriver(); }

    @BeforeEach
    public void BeforeEach() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }
    @AfterEach
    void tearDown(){
        driver.quit();
        driver = null;
    }

    @BeforeAll

    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }
     @Test
    void positiveFormChecksTest(){
         driver.findElement(By.cssSelector("[data-test-id = 'name'] input")).sendKeys("Лихачева Анастасия");
         driver.findElement(By.cssSelector("[data-test-id = 'phone'] input")).sendKeys("+79152489672");
         driver.findElement(By.cssSelector("[data-test-id = 'agreement']")).click();
         driver.findElement(By.cssSelector("button.button")).click();
         var actualText = driver.findElement(By.cssSelector("[data-test-id = 'order-success']")).getText();
         assertEquals("  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время. ",actualText);
     }
}
