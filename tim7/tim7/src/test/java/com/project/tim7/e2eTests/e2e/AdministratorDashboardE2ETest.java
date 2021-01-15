package com.project.tim7.e2eTests.e2e;

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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdministratorDashboardE2ETest {

    private WebDriver driver;

    private AdministratorDashboardPage administratorDashboardPage;
    private MainPagePage mainPagePage;
    private SignInPage signInPage;


    @Before
    public void setUp() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        administratorDashboardPage = PageFactory.initElements(driver, AdministratorDashboardPage.class);
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
    public void deleteAdminTestSuccess() throws InterruptedException {

        mainPagePage.getAdminDashboardNav().click();

        justWait(500);

       administratorDashboardPage.getDeleteBtn().click();

        justWait(500);

        String snackBarValue =driver.findElement(By.tagName("simple-snack-bar")).getText();

        administratorDashboardPage.ensureIsNotVisibleDeleteBtn();

        assertEquals("Administrator deleted.\nOk", snackBarValue);
        assertEquals("http://localhost:4200/administrator/dashboard", driver.getCurrentUrl());
    }
}
