package com.project.tim7.e2eTests.e2e;

import com.project.tim7.e2eTests.pages.AddAdministratorPage;
import com.project.tim7.e2eTests.pages.MainPagePage;
import com.project.tim7.e2eTests.pages.SignInPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AddAdministratorE2ETest {

    private WebDriver driver;

    @Autowired
    private TestRestTemplate restTemplate;

    private AddAdministratorPage addAdministratorPage;
    private MainPagePage mainPagePage;
    private SignInPage signInPage;


    @Before
    public void setUp() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        addAdministratorPage = PageFactory.initElements(driver, AddAdministratorPage.class);
        signInPage = PageFactory.initElements(driver, SignInPage.class);
        mainPagePage = PageFactory.initElements(driver, MainPagePage.class);

        //sign in
        driver.get("http://localhost:4200/sign-in");
        signInPage.getUsername().sendKeys("mico");
        signInPage.getPassword().sendKeys("123qweASD");
        signInPage.getSignInBtn().click();
        justWait(1000);
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
    public void addAdminTestSuccess() throws InterruptedException {

        mainPagePage.getAddAdminNav().click();

        justWait(500);

        addAdministratorPage.ensureIsDisplayedUsername();
        addAdministratorPage.ensureIsDisplayedEmail();

        addAdministratorPage.getUsername().sendKeys("newAdmin");

        addAdministratorPage.getEmail().sendKeys("new_admin@email.com");

        addAdministratorPage.getPassword().sendKeys("123qweASD");
        addAdministratorPage.getPasswordConfirm().sendKeys("123qweASD");

        addAdministratorPage.getAddAdminBtn().click();

        justWait(500);

        String snackBarValue = driver.findElement(By.tagName("simple-snack-bar")).getText();

        assertEquals("Administrator added.\nOk", snackBarValue);
        assertEquals("http://localhost:4200/administrator/add-administrator", driver.getCurrentUrl());

/*
        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/administrators/4", HttpMethod.DELETE, httpEntity,
                        String.class);

 */
    }

    @Test
    public void addAdminTestExistUsernameError() throws InterruptedException {

        mainPagePage.getAddAdminNav().click();

        justWait(500);

        addAdministratorPage.ensureIsDisplayedUsername();
        addAdministratorPage.ensureIsDisplayedEmail();

        addAdministratorPage.getUsername().sendKeys("mico");

        addAdministratorPage.getEmail().sendKeys("new_admin@email.com");

        addAdministratorPage.getPassword().sendKeys("123qweASD");
        addAdministratorPage.getPasswordConfirm().sendKeys("123qweASD");

        addAdministratorPage.getAddAdminBtn().click();

        justWait(500);

        String snackBarValue = driver.findElement(By.tagName("simple-snack-bar")).getText();

        assertEquals("Username or email already exists.\nOk", snackBarValue);
        assertEquals("http://localhost:4200/administrator/add-administrator", driver.getCurrentUrl());
    }

    @Test
    public void addAdminTestExistEmailError() throws InterruptedException {

        mainPagePage.getAddAdminNav().click();

        justWait(500);

        addAdministratorPage.ensureIsDisplayedUsername();
        addAdministratorPage.ensureIsDisplayedEmail();

        addAdministratorPage.getUsername().sendKeys("newAdmin");

        addAdministratorPage.getEmail().sendKeys("mico@admin.com");

        addAdministratorPage.getPassword().sendKeys("123qweASD");
        addAdministratorPage.getPasswordConfirm().sendKeys("123qweASD");

        addAdministratorPage.getAddAdminBtn().click();

        justWait(500);

        String snackBarValue = driver.findElement(By.tagName("simple-snack-bar")).getText();

        assertEquals("Username or email already exists.\nOk", snackBarValue);
        assertEquals("http://localhost:4200/administrator/add-administrator", driver.getCurrentUrl());
    }

    @Test
    public void addAdminTestPasswordMatchError() throws InterruptedException {
        mainPagePage.getAddAdminNav().click();

        justWait(500);

        addAdministratorPage.ensureIsDisplayedUsername();

        addAdministratorPage.getUsername().sendKeys("newAdmin");

        addAdministratorPage.getEmail().sendKeys("new_admin@email.com");

        addAdministratorPage.getPassword().sendKeys("123qweASD");
        addAdministratorPage.getPasswordConfirm().sendKeys("123qweASDa");

        String error = driver.findElement(By.tagName("mat-error")).getText();

        assertFalse(addAdministratorPage.getAddAdminBtn().isEnabled());
        assertEquals("Passwords don't mach!", error);
        assertEquals("http://localhost:4200/administrator/add-administrator", driver.getCurrentUrl());
    }

    @Test
    public void addAdminPasswordShortError() throws InterruptedException {
        mainPagePage.getAddAdminNav().click();

        justWait(500);

        addAdministratorPage.ensureIsDisplayedUsername();

        addAdministratorPage.getUsername().sendKeys("newAdmin");

        addAdministratorPage.getEmail().sendKeys("new_admin@email.com");

        addAdministratorPage.getPassword().sendKeys("123");
        addAdministratorPage.getPasswordConfirm().sendKeys("123");

        justWait(100);

        String error = driver.findElement(By.tagName("mat-error")).getText();

        assertFalse(addAdministratorPage.getAddAdminBtn().isEnabled());
        assertEquals("Password minimum length is 8!", error);
        assertEquals("http://localhost:4200/administrator/add-administrator", driver.getCurrentUrl());
    }

    @Test
    public void addAdminEmailError() throws InterruptedException {
        mainPagePage.getAddAdminNav().click();

        justWait(500);

        addAdministratorPage.ensureIsDisplayedUsername();

        addAdministratorPage.getUsername().sendKeys("newAdmin");

        addAdministratorPage.getEmail().sendKeys("new_admin");

        addAdministratorPage.getPassword().sendKeys("123qweASD");
        addAdministratorPage.getPasswordConfirm().sendKeys("123qweASD");

        justWait(100);

        String error = driver.findElement(By.tagName("mat-error")).getText();

        assertFalse(addAdministratorPage.getAddAdminBtn().isEnabled());
        assertEquals("Invalid email format!", error);
        assertEquals("http://localhost:4200/administrator/add-administrator", driver.getCurrentUrl());
    }

    @Test
    public void addAdminUsernameError() throws InterruptedException {
        mainPagePage.getAddAdminNav().click();

        justWait(500);

        addAdministratorPage.ensureIsDisplayedUsername();

        addAdministratorPage.getUsername().sendKeys("");

        addAdministratorPage.getEmail().sendKeys("new_admin@email.com");

        addAdministratorPage.getPassword().sendKeys("123qweASD");
        addAdministratorPage.getPasswordConfirm().sendKeys("123qweASD");

        justWait(100);

        String error = driver.findElement(By.tagName("mat-error")).getText();

        assertFalse(addAdministratorPage.getAddAdminBtn().isEnabled());
        assertEquals("This field can't be empty!", error);
        assertEquals("http://localhost:4200/administrator/add-administrator", driver.getCurrentUrl());
    }
}
