package com.project.tim7.e2e;

import com.project.tim7.pages.SignInPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SignInE2ETest {

    private WebDriver driver;

    private SignInPage signInPage;


    @Before
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        signInPage = PageFactory.initElements(driver, SignInPage.class);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void signInTestSuccess() throws InterruptedException {

        driver.get("http://localhost:4200/sign-in");

        justWait();

        signInPage.ensureIsDisplayedUsername();
        signInPage.ensureIsDisplayedPassword();

        signInPage.getUsername().sendKeys("mico");

        signInPage.getPassword().sendKeys("123qweASD");

        signInPage.getSignInBtn().click();

        signInPage.ensureIsNotVisibleSignInBtn();

        signInPage.ensureIsNotVisibleUsername();
        signInPage.ensureIsNotVisiblePassowrd();

        assertEquals("http://localhost:4200/", driver.getCurrentUrl());

    }

    @Test
    public void signInTestInvalidUsernameError() throws InterruptedException {

        driver.get("http://localhost:4200/sign-in");

        justWait();

        signInPage.ensureIsDisplayedUsername();
        signInPage.ensureIsDisplayedPassword();

        signInPage.getUsername().sendKeys("123123123");

        signInPage.getPassword().sendKeys("123qweASD");

        signInPage.getSignInBtn().click();

        justWait();

        String snackBarValue =driver.findElement(By.tagName("simple-snack-bar")).getText();

        assertEquals("Bad credentials!\nOk", snackBarValue);
        assertEquals("http://localhost:4200/sign-in", driver.getCurrentUrl());

    }

    @Test
    public void signTestInvalidPasswordError() throws InterruptedException {

        driver.get("http://localhost:4200/sign-in");

        justWait();

        signInPage.ensureIsDisplayedUsername();
        signInPage.ensureIsDisplayedPassword();

        signInPage.getUsername().sendKeys("mico");

        signInPage.getPassword().sendKeys("1111111111");

        signInPage.getSignInBtn().click();

        justWait();

        String snackBarValue =driver.findElement(By.tagName("simple-snack-bar")).getText();

        assertEquals("Bad credentials!\nOk", snackBarValue);
        assertEquals("http://localhost:4200/sign-in", driver.getCurrentUrl());
        assertEquals("http://localhost:4200/sign-in", driver.getCurrentUrl());

    }

    private void justWait() throws InterruptedException {
        synchronized (driver)
        {
            driver.wait(500);
        }
    }
}
