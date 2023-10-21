package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DebitCardTestNegative {
    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://127.0.0.1:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldBeFailedIncorrectNameInput() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Esenin Sergei");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79270000000");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();

        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",
                driver.findElement(By.cssSelector("[data-test-id=name].input_invalid. input__sub")).getText().trim());
    }

    @Test
    void shouldBeFailedEmptyNameInput() {
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79270000000");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();

        assertEquals("Поле обязательно для заполнения",
                driver.findElement(By.cssSelector("[data-test-id=name].input_invalid. input__sub")).getText().trim());
    }

    @Test
    void shouldBeFailedEmptyPhoneInput() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Салтыков-Щедрин Михаил");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();

        assertEquals("Поле обязательно для заполнения",
                driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid. input__sub")).getText().trim());
    }

    @Test
    void shouldBeFailedIncorrectPhoneInput() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Салтыков-Щедрин Михаил");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+700000000000000000");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();

        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",
                driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid. input__sub")).getText().trim());
    }

    @Test
    void shouldBeFailedEmptyCheckbox() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Салтыков-Щедрин Михаил");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79270000000");
        driver.findElement(By.cssSelector("button.button")).click();

        assertTrue(driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid")).isDisplayed());
    }

}