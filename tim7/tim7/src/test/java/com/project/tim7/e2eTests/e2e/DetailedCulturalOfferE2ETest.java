package com.project.tim7.e2eTests.e2e;

import com.project.tim7.e2eTests.pages.DetailedCulturalOfferPage;
import com.project.tim7.e2eTests.pages.MainPagePage;
import com.project.tim7.e2eTests.pages.SignInPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DetailedCulturalOfferE2ETest {

    private WebDriver driver;

    private SignInPage signInPage;
    private MainPagePage mainPagePage;
    private DetailedCulturalOfferPage detailedCulturalOfferPage;

    @Before
    public void setUp() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        detailedCulturalOfferPage = PageFactory.initElements(driver, DetailedCulturalOfferPage.class);
        signInPage = PageFactory.initElements(driver, SignInPage.class);
        mainPagePage = PageFactory.initElements(driver, MainPagePage.class);

        //sign in
        driver.get("http://localhost:4200/sign-in");
        // logging in as administrator
        signInPage.getUsername().sendKeys("micoR");
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
    public void testCommentNavigation() throws InterruptedException{
        Actions actions = new Actions(driver);
        actions.doubleClick(mainPagePage.getDetailedCulturalOfferRow()).perform();

        justWait(2000);

        assertEquals("http://localhost:4200/detailed-cultural-offer?offer_id=2", driver.getCurrentUrl());

        justWait(1000);

        detailedCulturalOfferPage.getNavigateCommentAfter().click();
        assertEquals("Curabitur gravida nisi at nibh.", detailedCulturalOfferPage.getCommentDescription().getText());

        justWait(1000);

        detailedCulturalOfferPage.getNavigateCommentBefore().click();
        assertEquals("Nullam sit amet turpis elementum ligula vehicula consequat. Morbi a ipsum.", detailedCulturalOfferPage.getCommentDescription().getText());

        assertEquals("http://localhost:4200/detailed-cultural-offer?offer_id=2", driver.getCurrentUrl());

    }

    @Test
    public void testNavigateNewsletter() throws InterruptedException {
        Actions actions = new Actions(driver);
        actions.doubleClick(mainPagePage.getDetailedCulturalOfferRow()).perform();

        justWait(2000);

        assertEquals("http://localhost:4200/detailed-cultural-offer?offer_id=2", driver.getCurrentUrl());

        justWait(1000);

        detailedCulturalOfferPage.getNavigateNewsletterAfter().click();
        assertEquals("Maecenas tristique, est et tempus semper, est quam pharetra magna, ac consequat metus sapien ut nunc.", detailedCulturalOfferPage.getNewsletterDescription().getText());

        justWait(1000);

        detailedCulturalOfferPage.getNavigateNewsletterBefore().click();
        assertEquals("Morbi ut odio.", detailedCulturalOfferPage.getNewsletterDescription().getText());

        assertEquals("http://localhost:4200/detailed-cultural-offer?offer_id=2", driver.getCurrentUrl());
    }

    @Test
    public void testAddRating() throws InterruptedException{
        Actions actions = new Actions(driver);
        actions.doubleClick(mainPagePage.getDetailedCulturalOfferRow()).perform();

        justWait(2000);

        assertEquals("http://localhost:4200/detailed-cultural-offer?offer_id=2", driver.getCurrentUrl());

        justWait(1000);

        detailedCulturalOfferPage.getRateButton().click();

        justWait(1000);

        assertEquals("Successfully rated!\nOk", detailedCulturalOfferPage.getSnackBar().getText());

        assertEquals("http://localhost:4200/detailed-cultural-offer?offer_id=2", driver.getCurrentUrl());

    }

    @Test
    public void testAddComment() throws InterruptedException{
        Actions actions = new Actions(driver);
        actions.doubleClick(mainPagePage.getDetailedCulturalOfferRow()).perform();

        justWait(2000);

        assertEquals("http://localhost:4200/detailed-cultural-offer?offer_id=2", driver.getCurrentUrl());

        justWait(1000);

        detailedCulturalOfferPage.getCommentInput().sendKeys("New comment!");
        detailedCulturalOfferPage.getPictureInput().sendKeys(new java.io.File( ".\\src\\test\\resources\\offer_photo.jpg" ).getAbsolutePath());
        detailedCulturalOfferPage.getPictureInput().sendKeys(new java.io.File(".\\src\\test\\resources\\offer_photo2.jpg").getAbsolutePath());

        justWait(1000);

        detailedCulturalOfferPage.getAddCommentButton().click();

        justWait(1000);

        assertEquals("Successfully commented!\nOk", detailedCulturalOfferPage.getSnackBar().getText());

        assertEquals("http://localhost:4200/detailed-cultural-offer?offer_id=2", driver.getCurrentUrl());

    }

    @Test
    public void testSubscribeUnsubscribe() throws InterruptedException{
        Actions actions = new Actions(driver);
        actions.doubleClick(mainPagePage.getDetailedCulturalOfferRow()).perform();

        justWait(1000);

        assertEquals("http://localhost:4200/detailed-cultural-offer?offer_id=2", driver.getCurrentUrl());

        justWait(1000);

        detailedCulturalOfferPage.getSubscribeButton().click();

        justWait(1000);

        assertEquals("Successfully subscribed!\nOk", detailedCulturalOfferPage.getSnackBar().getText());

        assertEquals("http://localhost:4200/detailed-cultural-offer?offer_id=2", driver.getCurrentUrl());

        justWait(1000);

        detailedCulturalOfferPage.getUnsubscribeButton().click();

        justWait(1000);

        assertEquals("Successfully unsubscribed!\nOk", detailedCulturalOfferPage.getSnackBar().getText());

        assertEquals("http://localhost:4200/detailed-cultural-offer?offer_id=2", driver.getCurrentUrl());

    }
}
