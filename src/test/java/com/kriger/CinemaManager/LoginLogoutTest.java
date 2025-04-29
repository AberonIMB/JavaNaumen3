package com.kriger.CinemaManager;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginLogoutTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void init() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testSuccessLogin() {
        driver.get("http://localhost:8080/login");
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        usernameField.sendKeys("2");

        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("111222");

        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        WebElement loginSuccess = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-success")));

        Assertions.assertEquals("Вы успешно авторизованы!", loginSuccess.getText());

    }

    @Test
    public void testSuccessLogout() {
        driver.get("http://localhost:8080/login");
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        usernameField.sendKeys("2");

        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("111222");

        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        WebElement logoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout-button")));
        Assertions.assertNotNull(logoutButton);
        Assertions.assertEquals("Logout", logoutButton.getText());

        logoutButton.click();

        WebElement logoutSuccess = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout-success")));
        Assertions.assertEquals("Вы вышли из системы", logoutSuccess.getText());

        Assertions.assertEquals("http://localhost:8080/auth/login?logout", driver.getCurrentUrl());
    }
}
