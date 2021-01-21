package com.project.tim7.e2eTests.e2e;

import com.project.tim7.e2eTests.pages.AddNewsletterPage;
import com.project.tim7.e2eTests.pages.DetailedCulturalOfferPage;
import com.project.tim7.e2eTests.pages.MainPagePage;
import com.project.tim7.e2eTests.pages.NewsletterDashboardPage;
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

//testovi za dodavanje ce proci samo pri prvom pokretanju nakon pokretanja servera, zbog generisanja id-a u bazi
//testovi za dodavanje nece raditi zajedno
public class AddAndDashboardNewsletterE2ETest {

    private WebDriver driver;

    @Autowired
    private TestRestTemplate restTemplate;

    private AddNewsletterPage addNewsletterPage;
    private NewsletterDashboardPage newsletterDashboardPage;
    private DetailedCulturalOfferPage detailedCulturalOfferPage;
    private MainPagePage mainPagePage;
    private SignInPage signInPage;


    @Before
    public void setUp() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        addNewsletterPage = PageFactory.initElements(driver, AddNewsletterPage.class);
        newsletterDashboardPage = PageFactory.initElements(driver, NewsletterDashboardPage.class);
        detailedCulturalOfferPage = PageFactory.initElements(driver, DetailedCulturalOfferPage.class);
        signInPage = PageFactory.initElements(driver, SignInPage.class);
        mainPagePage = PageFactory.initElements(driver, MainPagePage.class);

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
    public void addAndDeleteNewsletterForCulturalOfferTestSuccess() throws InterruptedException {
    	
    	Actions actions = new Actions(driver);
        actions.doubleClick(mainPagePage.getDetailedCulturalOfferRow()).perform();
        
        detailedCulturalOfferPage.getPublishNewsletterButton().click();
    	
        justWait(1000);

        addNewsletterPage.ensureIsDisplayedName();

        addNewsletterPage.getName().sendKeys("Novi muzej");
        addNewsletterPage.getDescription().sendKeys("Ovo je novi muzej");
		String picture = new java.io.File( ".\\src\\test\\resources\\newsletter_picture.jpg" ).getAbsolutePath();        
        addNewsletterPage.getPicture().sendKeys(picture);    
        
        addNewsletterPage.getPublishButton().click();

        justWait(3000);

        String snackBarValue = addNewsletterPage.getSnackBar().getText();

        assertEquals("Newsletter added.\nOk", snackBarValue);
        assertEquals("http://localhost:4200/newsletter/dashboard", driver.getCurrentUrl());
        
        mainPagePage.getNewsletterDashboardNav().click();
   
        for (int i = 0; i <= 100; i++) {
        	newsletterDashboardPage.getNextPageBtn().click();
        }
        justWait(1000);
        newsletterDashboardPage.ensureVisibleDeleteBtn();

        newsletterDashboardPage.getDeleteBtn().click();

        justWait(1000);

        String snackBarValueDeleted = newsletterDashboardPage.getSnackBar().getText();

        newsletterDashboardPage.ensureIsNotVisibleDeleteBtn();

        assertEquals("Newsletter deleted.\nOk", snackBarValueDeleted);
        assertEquals("http://localhost:4200/newsletter/dashboard", driver.getCurrentUrl());
    }
    

    @Test
    public void addAndDeleteNewsletterTestSuccess() throws InterruptedException {
    	
        mainPagePage.getPublishNewsletterNav().click();

        justWait(1000);

        addNewsletterPage.ensureIsDisplayedCategorySelect();
        addNewsletterPage.ensureIsDisplayedName();
        addNewsletterPage.getCategorySelect().sendKeys("Institucija");
        addNewsletterPage.getSubcategorySelect().sendKeys("Muzej");
        addNewsletterPage.getOfferSelect().sendKeys("Muzej Vostanih figura");
        addNewsletterPage.getName().sendKeys("Novi muzej");
        addNewsletterPage.getDescription().sendKeys("Ovo je novi muzej");
		String picture = new java.io.File( ".\\src\\test\\resources\\newsletter_picture.jpg" ).getAbsolutePath();        
        addNewsletterPage.getPicture().sendKeys(picture);    
        
        addNewsletterPage.getPublishButton().click();

        justWait(3000);

        String snackBarValue = addNewsletterPage.getSnackBar().getText();

        assertEquals("Newsletter added.\nOk", snackBarValue);
        assertEquals("http://localhost:4200/newsletter/dashboard", driver.getCurrentUrl());
        
        mainPagePage.getNewsletterDashboardNav().click();
   
        for (int i = 0; i <= 100; i++) {
        	newsletterDashboardPage.getNextPageBtn().click();
        }
        justWait(1000);
        newsletterDashboardPage.ensureVisibleDeleteBtn();

        newsletterDashboardPage.getDeleteBtn().click();

        justWait(1000);

        String snackBarValueDeleted = newsletterDashboardPage.getSnackBar().getText();

        newsletterDashboardPage.ensureIsNotVisibleDeleteBtn();

        assertEquals("Newsletter deleted.\nOk", snackBarValueDeleted);
        assertEquals("http://localhost:4200/newsletter/dashboard", driver.getCurrentUrl());
    }
    
    @Test
    public void paginationNextBackTestSuccess() throws InterruptedException {

        mainPagePage.getNewsletterDashboardNav().click();

        justWait(1000);

        newsletterDashboardPage.getNextPageBtn().click();

        String currentPageNext = newsletterDashboardPage.getCurrentPage().getText();

        assertEquals("11 – 20 of 1000", currentPageNext);


        newsletterDashboardPage.getPreviousPageBtn().click();

        String currentPagePrevious = newsletterDashboardPage.getCurrentPage().getText();

        assertEquals("1 – 10 of 1000", currentPagePrevious);
        assertEquals("http://localhost:4200/newsletter/dashboard", driver.getCurrentUrl());
    }

}
