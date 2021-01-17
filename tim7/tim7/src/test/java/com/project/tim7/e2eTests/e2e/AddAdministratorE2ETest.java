package com.project.tim7.e2eTests.e2e;

import com.project.tim7.e2eTests.pages.AddAdministratorPage;
import com.project.tim7.e2eTests.pages.AdministratorDashboardPage;
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
    private AdministratorDashboardPage administratorDashboardPage;


    @Before
    public void setUp() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        addAdministratorPage = PageFactory.initElements(driver, AddAdministratorPage.class);
        signInPage = PageFactory.initElements(driver, SignInPage.class);
        mainPagePage = PageFactory.initElements(driver, MainPagePage.class);
        administratorDashboardPage = PageFactory.initElements(driver, AdministratorDashboardPage.class);

        //sign in
        driver.get("http://localhost:4200/sign-in");
        signInPage.getUsername().sendKeys("mico");
        signInPage.getPassword().sendKeys("123qweASD");
        signInPage.getSignInBtn().click();
        justWait(3000);
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

        justWait(1000);

        addAdministratorPage.ensureIsDisplayedUsername();
        addAdministratorPage.ensureIsDisplayedEmail();

        addAdministratorPage.getUsername().sendKeys("newAdmin");

        addAdministratorPage.getEmail().sendKeys("new_admin@email.com");

        addAdministratorPage.getPassword().sendKeys("123qweASD");
        addAdministratorPage.getPasswordConfirm().sendKeys("123qweASD");

        addAdministratorPage.getAddAdminBtn().click();

        justWait(1000);

        String snackBarValue = addAdministratorPage.getSnackBar().getText();

        assertEquals("Administrator added.\nOk", snackBarValue);
        assertEquals("http://localhost:4200/administrator/add-administrator", driver.getCurrentUrl());

        addAdministratorPage.getAdminDashboardNavigate().click();
        justWait(2000);
        driver.findElement(By.xpath("//*[@aria-label=\"Next page\"]")).click();
        administratorDashboardPage.getDeleteBtn().click();
        justWait(1000);

        snackBarValue = administratorDashboardPage.getSnackBar().getText();

        administratorDashboardPage.ensureIsNotVisibleDeleteBtn();

        assertEquals("Administrator deleted.\nOk", snackBarValue);
        assertEquals("http://localhost:4200/administrator/dashboard", driver.getCurrentUrl());
    }

    @Test
    public void addAdminTestExistUsernameError() throws InterruptedException {

        mainPagePage.getAddAdminNav().click();

        justWait(1000);

        addAdministratorPage.ensureIsDisplayedUsername();
        addAdministratorPage.ensureIsDisplayedEmail();

        addAdministratorPage.getUsername().sendKeys("mico");

        addAdministratorPage.getEmail().sendKeys("new_admin@email.com");

        addAdministratorPage.getPassword().sendKeys("123qweASD");
        addAdministratorPage.getPasswordConfirm().sendKeys("123qweASD");

        addAdministratorPage.getAddAdminBtn().click();

        justWait(1000);

        String snackBarValue = addAdministratorPage.getSnackBar().getText();

        assertEquals("Username or email already exists.\nOk", snackBarValue);
        assertEquals("http://localhost:4200/administrator/add-administrator", driver.getCurrentUrl());
    }

    @Test
    public void addAdminTestExistEmailError() throws InterruptedException {

        mainPagePage.getAddAdminNav().click();

        justWait(1000);

        addAdministratorPage.ensureIsDisplayedUsername();
        addAdministratorPage.ensureIsDisplayedEmail();

        addAdministratorPage.getUsername().sendKeys("newAdmin");

        addAdministratorPage.getEmail().sendKeys("mico@admin.com");

        addAdministratorPage.getPassword().sendKeys("123qweASD");
        addAdministratorPage.getPasswordConfirm().sendKeys("123qweASD");

        addAdministratorPage.getAddAdminBtn().click();

        justWait(1000);

        String snackBarValue = addAdministratorPage.getSnackBar().getText();

        assertEquals("Username or email already exists.\nOk", snackBarValue);
        assertEquals("http://localhost:4200/administrator/add-administrator", driver.getCurrentUrl());
    }

    @Test
    public void addAdminTestPasswordMatchError() throws InterruptedException {
        mainPagePage.getAddAdminNav().click();

        justWait(1000);

        addAdministratorPage.ensureIsDisplayedUsername();

        addAdministratorPage.getUsername().sendKeys("newAdmin");

        addAdministratorPage.getEmail().sendKeys("new_admin@email.com");

        addAdministratorPage.getPassword().sendKeys("123qweASD");
        addAdministratorPage.getPasswordConfirm().sendKeys("123qweASDa");

        String errorValue = addAdministratorPage.getError().getText();

        assertFalse(addAdministratorPage.getAddAdminBtn().isEnabled());
        assertEquals("Passwords don't mach!", errorValue);
        assertEquals("http://localhost:4200/administrator/add-administrator", driver.getCurrentUrl());
    }

    @Test
    public void addAdminPasswordShortError() throws InterruptedException {
        mainPagePage.getAddAdminNav().click();

        justWait(1000);

        addAdministratorPage.ensureIsDisplayedUsername();

        addAdministratorPage.getUsername().sendKeys("newAdmin");

        addAdministratorPage.getEmail().sendKeys("new_admin@email.com");

        addAdministratorPage.getPassword().sendKeys("123");
        addAdministratorPage.getPasswordConfirm().sendKeys("123");

        justWait(100);

        String errorValue = addAdministratorPage.getError().getText();

        assertFalse(addAdministratorPage.getAddAdminBtn().isEnabled());
        assertEquals("Password minimum length is 8!", errorValue);
        assertEquals("http://localhost:4200/administrator/add-administrator", driver.getCurrentUrl());
    }

    @Test
    public void addAdminEmailError() throws InterruptedException {
        mainPagePage.getAddAdminNav().click();

        justWait(1000);

        addAdministratorPage.ensureIsDisplayedUsername();

        addAdministratorPage.getUsername().sendKeys("newAdmin");

        addAdministratorPage.getEmail().sendKeys("new_admin");

        addAdministratorPage.getPassword().sendKeys("123qweASD");
        addAdministratorPage.getPasswordConfirm().sendKeys("123qweASD");

        justWait(100);

        String errorValue = addAdministratorPage.getError().getText();

        assertFalse(addAdministratorPage.getAddAdminBtn().isEnabled());
        assertEquals("Invalid email format!", errorValue);
        assertEquals("http://localhost:4200/administrator/add-administrator", driver.getCurrentUrl());
    }

    @Test
    public void addAdminUsernameError() throws InterruptedException {
        mainPagePage.getAddAdminNav().click();

        justWait(1000);

        addAdministratorPage.ensureIsDisplayedUsername();

        addAdministratorPage.getUsername().sendKeys("");

        addAdministratorPage.getEmail().sendKeys("new_admin@email.com");

        addAdministratorPage.getPassword().sendKeys("123qweASD");
        addAdministratorPage.getPasswordConfirm().sendKeys("123qweASD");

        justWait(100);

        String errorValue = addAdministratorPage.getError().getText();

        assertFalse(addAdministratorPage.getAddAdminBtn().isEnabled());
        assertEquals("This field can't be empty!", errorValue);
        assertEquals("http://localhost:4200/administrator/add-administrator", driver.getCurrentUrl());
    }
}
