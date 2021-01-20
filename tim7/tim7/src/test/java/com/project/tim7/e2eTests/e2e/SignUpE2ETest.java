package com.project.tim7.e2eTests.e2e;

import com.project.tim7.e2eTests.pages.SignUpPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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

    private void justWait(Integer howLong) throws InterruptedException {
        synchronized (driver)
        {
            driver.wait(howLong);
        }
    }

    @Test
    public void signUpTestSuccess() throws InterruptedException {

        driver.get("http://localhost:4200/sign-up");

        justWait(1000);

        signUpPage.ensureIsDisplayedUsername();
        signUpPage.ensureIsDisplayedEmail();

        signUpPage.getUsername().sendKeys("newUser");

        signUpPage.getEmail().sendKeys("new_user@email.com");

        signUpPage.getPassword().sendKeys("123qweASD");
        signUpPage.getPasswordConfirm().sendKeys("123qweASD");

        signUpPage.getSignUpBtn().click();

        signUpPage.ensureIsNotVisibleSignUpBtn();

        signUpPage.ensureIsNotVisibleUsername();

        justWait(1000);

        String snackBarValue = signUpPage.getSnackBar().getText();

        assertEquals("Registration successful! Activate account by email.\nOk", snackBarValue);
        assertEquals("http://localhost:4200/", driver.getCurrentUrl());
    }

    @Test
    public void signUpTestExistUsernameError() throws InterruptedException {

        driver.get("http://localhost:4200/sign-up");

        justWait(1000);

        signUpPage.ensureIsDisplayedUsername();

        signUpPage.getUsername().sendKeys("mico");

        signUpPage.getEmail().sendKeys("new_user1@email.com");

        signUpPage.getPassword().sendKeys("123qweASD");
        signUpPage.getPasswordConfirm().sendKeys("123qweASD");

        signUpPage.getSignUpBtn().click();

        justWait(1000);

        String snackBarValue = signUpPage.getSnackBar().getText();

        assertEquals("Username or email already exists.\nOk", snackBarValue);

        assertEquals("http://localhost:4200/sign-up", driver.getCurrentUrl());
    }

    @Test
    public void signUpTestExistEmailError() throws InterruptedException {

        driver.get("http://localhost:4200/sign-up");

        justWait(1000);

        signUpPage.ensureIsDisplayedUsername();

        signUpPage.getUsername().sendKeys("newUser1");

        signUpPage.getEmail().sendKeys("mico@admin.com");

        signUpPage.getPassword().sendKeys("123qweASD");
        signUpPage.getPasswordConfirm().sendKeys("123qweASD");

        signUpPage.getSignUpBtn().click();

        justWait(1000);
        String snackBarValue = signUpPage.getSnackBar().getText();

        assertEquals("Username or email already exists.\nOk", snackBarValue);

        assertEquals("http://localhost:4200/sign-up", driver.getCurrentUrl());
    }

    @Test
    public void signUpTestEmailError() throws InterruptedException {
        driver.get("http://localhost:4200/sign-up");

        justWait(1000);

        signUpPage.ensureIsDisplayedUsername();

        signUpPage.getUsername().sendKeys("newUser2");

        signUpPage.getEmail().sendKeys("new_user2");

        signUpPage.getPassword().sendKeys("123qweASD");
        signUpPage.getPasswordConfirm().sendKeys("123qweASD");

        justWait(1000);
        String errorValue = signUpPage.getError().getText();

        assertFalse(signUpPage.getSignUpBtn().isEnabled());
        assertEquals("Invalid email format!", errorValue);
        assertEquals("http://localhost:4200/sign-up", driver.getCurrentUrl());
    }

    @Test
    public void signUpTestUsernameError() throws InterruptedException {
        driver.get("http://localhost:4200/sign-up");

        justWait(1000);

        signUpPage.ensureIsDisplayedUsername();

        signUpPage.getUsername().sendKeys("");

        signUpPage.getEmail().sendKeys("new_user2@email.com");

        signUpPage.getPassword().sendKeys("123qweASD");
        signUpPage.getPasswordConfirm().sendKeys("123qweASD");

        justWait(1000);
        String errorValue = signUpPage.getError().getText();

        assertFalse(signUpPage.getSignUpBtn().isEnabled());
        assertEquals("This field can't be empty!", errorValue);
        assertEquals("http://localhost:4200/sign-up", driver.getCurrentUrl());
    }

    @Test
    public void signUpTestPasswordMatchError() throws InterruptedException {
        driver.get("http://localhost:4200/sign-up");

        justWait(1000);

        signUpPage.ensureIsDisplayedUsername();

        signUpPage.getUsername().sendKeys("newUser2");

        signUpPage.getEmail().sendKeys("new_user2@email.com");

        signUpPage.getPassword().sendKeys("123qweASD");
        signUpPage.getPasswordConfirm().sendKeys("123qweASDa");

        justWait(1000);
        String errorValue = signUpPage.getError().getText();

        assertFalse(signUpPage.getSignUpBtn().isEnabled());
        assertEquals("Passwords don't mach!", errorValue);
        assertEquals("http://localhost:4200/sign-up", driver.getCurrentUrl());
    }

    @Test
    public void signUpTestPasswordShortError() throws InterruptedException {
        driver.get("http://localhost:4200/sign-up");

        justWait(1000);

        signUpPage.ensureIsDisplayedUsername();

        signUpPage.getUsername().sendKeys("newUser2");

        signUpPage.getEmail().sendKeys("new_user2@email.com");

        signUpPage.getPassword().sendKeys("123");
        signUpPage.getPasswordConfirm().sendKeys("123");

        justWait(1000);
        String errorValue = signUpPage.getError().getText();

        assertFalse(signUpPage.getSignUpBtn().isEnabled());
        assertEquals("Password minimum length is 8!", errorValue);
        assertEquals("http://localhost:4200/sign-up", driver.getCurrentUrl());
    }
}
