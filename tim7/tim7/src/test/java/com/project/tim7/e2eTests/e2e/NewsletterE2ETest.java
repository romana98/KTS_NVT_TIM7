package com.project.tim7.e2eTests.e2e;

import com.project.tim7.e2eTests.pages.AddNewsletterPage;
import com.project.tim7.e2eTests.pages.DetailedCulturalOfferPage;
import com.project.tim7.e2eTests.pages.MainPagePage;
import com.project.tim7.e2eTests.pages.NewsletterDashboardPage;
import com.project.tim7.e2eTests.pages.NewsletterPage;
import com.project.tim7.e2eTests.pages.SignInPage;
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

public class NewsletterE2ETest {

    private WebDriver driver;

    @Autowired
    private TestRestTemplate restTemplate;

    private NewsletterPage newsletterPage;
    private DetailedCulturalOfferPage detailedCulturalOfferPage;
    private MainPagePage mainPagePage;
    private SignInPage signInPage;


    @Before
    public void setUp() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        newsletterPage = PageFactory.initElements(driver, NewsletterPage.class);
        detailedCulturalOfferPage = PageFactory.initElements(driver, DetailedCulturalOfferPage.class);
        signInPage = PageFactory.initElements(driver, SignInPage.class);
        mainPagePage = PageFactory.initElements(driver, MainPagePage.class);

        //sign in
        driver.get("http://localhost:4200/sign-in");
        signInPage.getUsername().sendKeys("minchboard3d");
        signInPage.getPassword().sendKeys("123qweASD");
        signInPage.getSignInBtn().click();
        justWait(5000);
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
    public void viewNewsletterDetailsTestSuccess() throws InterruptedException {
        mainPagePage.getNewsletterSubscribedNav().click();
        
        justWait(1000);
        
        newsletterPage.getCategoryTab().click();
        
        justWait(1000);
        
        newsletterPage.getDetailsButton().click();
        
        justWait(1000);

        assertEquals(newsletterPage.getDialogTitle().getText(), "Newsletter629 (CulturalOffer829)");
        assertEquals("http://localhost:4200/newsletter/subscribed-newsletter", driver.getCurrentUrl());
    }
    
    @Test
    public void unsibscribeTestSuccess() throws InterruptedException {
        mainPagePage.getNewsletterSubscribedNav().click();
        
        justWait(1000);

        newsletterPage.getCategoryTab().click();

        justWait(1000);
        
        newsletterPage.getUnsubscribeButton().click();
        
        justWait(1000);
        
        String snackBarValue = newsletterPage.getSnackBar().getText();
        assertEquals("Successfully unsubscribed.\nOk", snackBarValue);
        assertEquals("http://localhost:4200/newsletter/subscribed-newsletter", driver.getCurrentUrl());
        
    	mainPagePage.getHomeNav().click();
    	mainPagePage.getFilterInput().sendKeys("829");
    	justWait(1000);
    	
    	Actions actions = new Actions(driver);
        actions.doubleClick(mainPagePage.getOfferToSubscribe()).perform();
    	
        justWait(1000);
        assertEquals("http://localhost:4200/detailed-cultural-offer?offer_id=834", driver.getCurrentUrl());
        
        detailedCulturalOfferPage.getSubscribeButton().click();
    }

}
