package com.project.tim7.e2eTests.e2e;

import com.project.tim7.e2eTests.pages.AddNewsletterPage;
import com.project.tim7.e2eTests.pages.MainPagePage;
import com.project.tim7.e2eTests.pages.NewsletterDashboardPage;
import com.project.tim7.e2eTests.pages.SignInPage;
import com.project.tim7.e2eTests.pages.UpdateNewsletterPage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;

public class UpdateNewsletterE2ETest {

    private WebDriver driver;

    @Autowired
    private TestRestTemplate restTemplate;

    private UpdateNewsletterPage updateNewsletterPage;
    private NewsletterDashboardPage newsletterDashboardPage;
    private MainPagePage mainPagePage;
    private SignInPage signInPage;


    @Before
    public void setUp() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        updateNewsletterPage = PageFactory.initElements(driver, UpdateNewsletterPage.class);
        newsletterDashboardPage = PageFactory.initElements(driver, NewsletterDashboardPage.class);
        signInPage = PageFactory.initElements(driver, SignInPage.class);
        mainPagePage = PageFactory.initElements(driver, MainPagePage.class);

        //sign in
        driver.get("http://localhost:4200/sign-in");
        signInPage.getUsername().sendKeys("mico");
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
    public void updateNewsletterTestSuccess() throws InterruptedException {
        mainPagePage.getNewsletterMenu().click();
        mainPagePage.getNewsletterDashboardNav().click();

        justWait(1000);
        
        for (int i = 0; i <= 99; i++) {
        	newsletterDashboardPage.getNextPageBtn().click();
        }
        Actions actions = new Actions(driver);
        actions.doubleClick(newsletterDashboardPage.getClickedRow()).perform();
        
        updateNewsletterPage.ensureIsDisplayedName();
        updateNewsletterPage.getName().sendKeys("Newsletter1000Updated");
        
        updateNewsletterPage.getUpdateButton().click();

        justWait(3000);

        String snackBarValue = updateNewsletterPage.getSnackBar().getText();

        assertEquals("Newsletter updated.\nOk", snackBarValue);
        assertEquals("http://localhost:4200/newsletter/dashboard", driver.getCurrentUrl());

        mainPagePage.getNewsletterMenu().click();
        mainPagePage.getNewsletterDashboardNav().click();
   
        for (int i = 0; i <= 99; i++) {
        	newsletterDashboardPage.getNextPageBtn().click();
        }
        
        actions.doubleClick(newsletterDashboardPage.getClickedRow()).perform();
        
        updateNewsletterPage.ensureIsDisplayedName();
        updateNewsletterPage.getName().sendKeys("Newsletter1000");

        justWait(1000);

        updateNewsletterPage.getUpdateButton().click();

        justWait(1000);

        String snackBarValueRestore = newsletterDashboardPage.getSnackBar().getText();

        assertEquals("Newsletter updated.\nOk", snackBarValueRestore);
    }

}
