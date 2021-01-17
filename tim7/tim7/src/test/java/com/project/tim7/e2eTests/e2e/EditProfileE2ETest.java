package com.project.tim7.e2eTests.e2e;

import com.project.tim7.e2eTests.pages.EditProfilePage;
import com.project.tim7.e2eTests.pages.MainPagePage;
import com.project.tim7.e2eTests.pages.SignInPage;
import com.project.tim7.e2eTests.pages.ViewProfilePage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class EditProfileE2ETest {
    private WebDriver driver;

    private EditProfilePage editProfilePage;
    private MainPagePage mainPagePage;
    private SignInPage signInPage;
    private ViewProfilePage viewProfilePage;


    @Before
    public void setUp() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        editProfilePage = PageFactory.initElements(driver, EditProfilePage.class);
        signInPage = PageFactory.initElements(driver, SignInPage.class);
        mainPagePage = PageFactory.initElements(driver, MainPagePage.class);
        viewProfilePage = PageFactory.initElements(driver, ViewProfilePage.class);

        //sign in
        driver.get("http://localhost:4200/sign-in");
        signInPage.getUsername().sendKeys("micoR");
        signInPage.getPassword().sendKeys("123qweASD");
        signInPage.getSignInBtn().click();
        justWait(1500);
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
    public void editProfileEditEmailSuccess() throws InterruptedException {

        mainPagePage.getViewProfileNav().click();
        viewProfilePage.getEditProfileBtn().click();

        justWait(1000);

        editProfilePage.ensureIsDisplayedUsername();
        editProfilePage.ensureIsDisplayedEmail();

        editProfilePage.getEmail().clear();
        editProfilePage.getEmail().sendKeys("new_admin@email.com");

        editProfilePage.getEditProfileBtn().click();

        justWait(1000);

        String snackBarValue = editProfilePage.getSnackBar().getText();

        assertEquals("Profile updated.\nOk", snackBarValue);
        assertEquals("http://localhost:4200/registered/view-profile", driver.getCurrentUrl());
    }

    @Test
    public void editProfileEditPasswordSuccess() throws InterruptedException {

        mainPagePage.getViewProfileNav().click();
        viewProfilePage.getEditProfileBtn().click();

        justWait(1000);

        editProfilePage.ensureIsDisplayedUsername();
        editProfilePage.ensureIsDisplayedEmail();

        editProfilePage.getPassword().sendKeys("123qweASD");
        editProfilePage.getPasswordConfirm().sendKeys("123qweASD");

        editProfilePage.getEditProfileBtn().click();

        justWait(1000);

        String snackBarValue = editProfilePage.getSnackBar().getText();

        assertEquals("Profile updated.\nOk", snackBarValue);
        assertEquals("http://localhost:4200/registered/view-profile", driver.getCurrentUrl());
    }

    @Test
    public void editProfileEditEmailError() throws InterruptedException {

        mainPagePage.getViewProfileNav().click();
        viewProfilePage.getEditProfileBtn().click();

        justWait(1000);

        editProfilePage.ensureIsDisplayedUsername();
        editProfilePage.ensureIsDisplayedEmail();

        editProfilePage.getEmail().clear();
        editProfilePage.getEmail().sendKeys("new_admin");

        String errorValue = editProfilePage.getError().getText();

        assertFalse(editProfilePage.getEditProfileBtn().isEnabled());
        assertEquals("Invalid email format!", errorValue);
        assertEquals("http://localhost:4200/registered/edit-profile", driver.getCurrentUrl());
    }

    @Test
    public void editProfileEditExistEmailError() throws InterruptedException {

        mainPagePage.getViewProfileNav().click();
        viewProfilePage.getEditProfileBtn().click();

        justWait(1000);

        editProfilePage.ensureIsDisplayedUsername();
        editProfilePage.ensureIsDisplayedEmail();

        editProfilePage.getEmail().clear();
        editProfilePage.getEmail().sendKeys("mico@admin.com");

        editProfilePage.getEditProfileBtn().click();

        justWait(1000);

        String snackBarValue = editProfilePage.getSnackBar().getText();

        assertEquals("Email already exists.\nOk", snackBarValue);
        assertEquals("http://localhost:4200/registered/edit-profile", driver.getCurrentUrl());
    }

    @Test
    public void editProfileEditPasswordMatchError() throws InterruptedException {

        mainPagePage.getViewProfileNav().click();
        viewProfilePage.getEditProfileBtn().click();

        justWait(1000);

        editProfilePage.ensureIsDisplayedUsername();
        editProfilePage.ensureIsDisplayedEmail();

        editProfilePage.getPassword().sendKeys("123qweASD");
        editProfilePage.getPasswordConfirm().sendKeys("123qweASDa");

        String errorValue = editProfilePage.getError().getText();

        assertFalse(editProfilePage.getEditProfileBtn().isEnabled());
        assertEquals("Passwords don't mach!", errorValue);
        assertEquals("http://localhost:4200/registered/edit-profile", driver.getCurrentUrl());
    }

    @Test
    public void editProfileEditPasswordShortError() throws InterruptedException {

        mainPagePage.getViewProfileNav().click();
        viewProfilePage.getEditProfileBtn().click();

        justWait(1000);

        editProfilePage.ensureIsDisplayedUsername();
        editProfilePage.ensureIsDisplayedEmail();

        editProfilePage.getPassword().sendKeys("123");
        editProfilePage.getPasswordConfirm().sendKeys("123");

        String errorValue = editProfilePage.getError().getText();

        assertFalse(editProfilePage.getEditProfileBtn().isEnabled());
        assertEquals("New password min length is 8!", errorValue);
        assertEquals("http://localhost:4200/registered/edit-profile", driver.getCurrentUrl());
    }
}
