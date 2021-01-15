package com.project.tim7.e2e;

import com.project.tim7.pages.AddAdministratorPage;
import com.project.tim7.pages.MainPagePage;
import com.project.tim7.pages.SignInPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AddAdministratorE2ETest {

    private WebDriver driver;

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
        justWait();
        signInPage.getUsername().sendKeys("mico");
        signInPage.getPassword().sendKeys("123qweASD");
        signInPage.getSignInBtn().click();
        justWait();
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
    public void AddAdminTestSuccess() throws InterruptedException {

        mainPagePage.getAddAdminNav().click();

        justWait();

        addAdministratorPage.ensureIsDisplayedUsername();
        addAdministratorPage.ensureIsDisplayedEmail();

        addAdministratorPage.getUsername().sendKeys("newAdmin");

        addAdministratorPage.getEmail().sendKeys("new_admin@email.com");

        addAdministratorPage.getPassword().sendKeys("123qweASD");
        addAdministratorPage.getPasswordConfirm().sendKeys("123qweASD");

        addAdministratorPage.getAddAdminBtn().click();

        addAdministratorPage.ensureIsNotVisibleAddAdminBtn();

        addAdministratorPage.ensureIsNotVisibleUsername();

        justWait();

        String snackBarValue =driver.findElement(By.tagName("simple-snack-bar")).getText();
        System.out.println(snackBarValue);

        assertEquals("Administrator added.\nOk", snackBarValue);
        //assertEquals("http://localhost:4200/administrator/add-administrator", driver.getCurrentUrl());
    }

    @Test
    public void SignUpTestExistUsernameError() throws InterruptedException {

        driver.get("http://localhost:4200/administrator/add-administrator");

        justWait();

        addAdministratorPage.ensureIsDisplayedUsername();

        addAdministratorPage.getUsername().sendKeys("newAdmin");

        addAdministratorPage.getEmail().sendKeys("new_admin1@email.com");

        addAdministratorPage.getPassword().sendKeys("123qweASD");
        addAdministratorPage.getPasswordConfirm().sendKeys("123qweASD");

        addAdministratorPage.getAddAdminBtn().click();

        justWait();

        String snackBarValue =driver.findElement(By.tagName("simple-snack-bar")).getText();

        assertEquals("Username or email already exists.\nOk", snackBarValue);

        assertEquals("http://localhost:4200/administrator/add-administrator", driver.getCurrentUrl());
    }

    @Test
    public void SignUpTestExistEmailError() throws InterruptedException {

        driver.get("http://localhost:4200/administrator/add-administrator");

        justWait();

        addAdministratorPage.ensureIsDisplayedUsername();

        addAdministratorPage.getUsername().sendKeys("newAdmin1");

        addAdministratorPage.getEmail().sendKeys("new_admin@email.com");

        addAdministratorPage.getPassword().sendKeys("123qweASD");
        addAdministratorPage.getPasswordConfirm().sendKeys("123qweASD");

        addAdministratorPage.getAddAdminBtn().click();

        justWait();

        String snackBarValue =driver.findElement(By.tagName("simple-snack-bar")).getText();

        assertEquals("Username or email already exists.\nOk", snackBarValue);

        assertEquals("http://localhost:4200/administrator/add-administrator", driver.getCurrentUrl());
    }

    @Test
    public void SignUpTestPasswordMatchError() throws InterruptedException {
        driver.get("http://localhost:4200/administrator/add-administrator");

        justWait();

        addAdministratorPage.ensureIsDisplayedUsername();

        addAdministratorPage.getUsername().sendKeys("newAdmin2");

        addAdministratorPage.getEmail().sendKeys("new_admin2@email.com");

        addAdministratorPage.getPassword().sendKeys("123qweASD");
        addAdministratorPage.getPasswordConfirm().sendKeys("123qweASDa");

        assertFalse(addAdministratorPage.getAddAdminBtn().isEnabled());
        assertEquals("http://localhost:4200/administrator/add-administrator", driver.getCurrentUrl());
    }
}
