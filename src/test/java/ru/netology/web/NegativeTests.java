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

public class NegativeTests {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestPhoneEmpty() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Костычев Михаил");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        var actualText = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals("Поле обязательно для заполнения", actualText);
    }

    @Test
    void shouldTestNameEmpty() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+70000000251");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        var actualText = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        assertEquals("Поле обязательно для заполнения", actualText);
    }

    @Test
    void shouldTestAgreementEmpty() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Ломоносов Евгений");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79195678491");
        driver.findElement(By.cssSelector("button.button")).click();
        var actualText = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid")).getText().trim();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", actualText);
    }

    @Test
    void shouldTestNameLat() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Lomonosov Евгений");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79678459123");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        var actualText = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actualText);
    }

    @Test
    void shouldTestPhoneMore() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Карпов Евгений");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+796784059123");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        var actualText = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actualText);
    }

    void shouldTestPhoneLetters() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Карпов Евгений");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+рпнтлдьрнров");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        var actualText = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actualText);
    }

    void shouldTestPhoneNoPlus() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Карпов Евгений");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("84526398755");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        var actualText = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actualText);
    }

}

