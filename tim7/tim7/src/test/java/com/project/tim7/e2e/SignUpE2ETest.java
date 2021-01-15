package com.project.tim7.e2e;

import com.project.tim7.pages.SignUpPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SignUpE2ETest {


    private WebDriver driver;

    private SignUpPage signUpPage;


    @Before
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        signUpPage = PageFactory.initElements(driver, SignUpPage.class);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    private void justWait() throws InterruptedException {
        synchronized (driver)
        {
            driver.wait(500);
        }
    }

    @Test
    public void SignUpTestSuccess() throws InterruptedException {

        driver.get("http://localhost:4200/sign-up");

        justWait();

        signUpPage.ensureIsDisplayedUsername();
        signUpPage.ensureIsDisplayedEmail();

        signUpPage.getUsername().sendKeys("newUser");

        signUpPage.getEmail().sendKeys("new_user@email.com");

        signUpPage.getPassword().sendKeys("123qweASD");
        signUpPage.getPasswordConfirm().sendKeys("123qweASD");

        signUpPage.getSignUpBtn().click();

        signUpPage.ensureIsNotVisibleSignUpBtn();

        signUpPage.ensureIsNotVisibleUsername();

        justWait();

        String snackBarValue =driver.findElement(By.tagName("simple-snack-bar")).getText();
        System.out.println(snackBarValue);

        assertEquals("Registration successful! Activate account by email.\nOk", snackBarValue);
        assertEquals("http://localhost:4200/", driver.getCurrentUrl());
    }

    @Test
    public void SignUpTestExistUsernameError() throws InterruptedException {

        driver.get("http://localhost:4200/sign-up");

        justWait();

        signUpPage.ensureIsDisplayedUsername();

        signUpPage.getUsername().sendKeys("newUser");

        signUpPage.getEmail().sendKeys("new_user1@email.com");

        signUpPage.getPassword().sendKeys("123qweASD");
        signUpPage.getPasswordConfirm().sendKeys("123qweASD");

        signUpPage.getSignUpBtn().click();

        justWait();

        String snackBarValue =driver.findElement(By.tagName("simple-snack-bar")).getText();

        assertEquals("Username or email already exists.\nOk", snackBarValue);

        assertEquals("http://localhost:4200/sign-up", driver.getCurrentUrl());
    }

    @Test
    public void SignUpTestExistEmailError() throws InterruptedException {

        driver.get("http://localhost:4200/sign-up");

        justWait();

        signUpPage.ensureIsDisplayedUsername();

        signUpPage.getUsername().sendKeys("newUser1");

        signUpPage.getEmail().sendKeys("new_user@email.com");

        signUpPage.getPassword().sendKeys("123qweASD");
        signUpPage.getPasswordConfirm().sendKeys("123qweASD");

        signUpPage.getSignUpBtn().click();

        justWait();

        String snackBarValue =driver.findElement(By.tagName("simple-snack-bar")).getText();

        assertEquals("Username or email already exists.\nOk", snackBarValue);

        assertEquals("http://localhost:4200/sign-up", driver.getCurrentUrl());
    }

    @Test
    public void SignUpTestPasswordMatchError() throws InterruptedException {
        driver.get("http://localhost:4200/sign-up");

        justWait();

        signUpPage.ensureIsDisplayedUsername();

        signUpPage.getUsername().sendKeys("newUser2");

        signUpPage.getEmail().sendKeys("new_user2@email.com");

        signUpPage.getPassword().sendKeys("123qweASD");
        signUpPage.getPasswordConfirm().sendKeys("123qweASDa");

        assertFalse(signUpPage.getSignUpBtn().isEnabled());
        assertEquals("http://localhost:4200/sign-up", driver.getCurrentUrl());
    }
}
