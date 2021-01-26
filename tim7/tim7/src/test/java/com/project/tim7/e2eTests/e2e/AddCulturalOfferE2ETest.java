package com.project.tim7.e2eTests.e2e;

import com.project.tim7.e2eTests.pages.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddCulturalOfferE2ETest {

    private WebDriver driver;

    @Autowired
    private TestRestTemplate restTemplate;

    private MainPagePage mainPagePage;
    private SignInPage signInPage;
    private AddCulturalOfferPage addCulturalOfferPage;
    private CulturalOfferDashboardPage culturalOfferDashboardPage;


    @Before
    public void setUp() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        addCulturalOfferPage = PageFactory.initElements(driver, AddCulturalOfferPage.class);
        signInPage = PageFactory.initElements(driver, SignInPage.class);
        mainPagePage = PageFactory.initElements(driver, MainPagePage.class);
        culturalOfferDashboardPage = PageFactory.initElements(driver, CulturalOfferDashboardPage.class);

        //sign in
        driver.get("http://localhost:4200/sign-in");
        signInPage.getUsername().sendKeys("mico");
        signInPage.getPassword().sendKeys("123qweASD");
        signInPage.getSignInBtn().click();
        justWait(4000);
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

    private void addCulturalOffer(String name, String dateStart, String dateEnd, String picture, String description){
        addCulturalOfferPage.getNameField().sendKeys(name);

        addCulturalOfferPage.getStartDateField().sendKeys(dateStart);
        addCulturalOfferPage.getEndDateField().sendKeys(dateEnd);

        addCulturalOfferPage.getDescriptionField().sendKeys(description);

        addCulturalOfferPage.getPictureField().sendKeys(picture);
    }

    @Test
    public void addCulturalOfferTestSuccess() throws InterruptedException {
        mainPagePage.getCulturalOfferMenu().click();
        mainPagePage.getCulturalOfferAddNav().click();

        justWait(2000);

        addCulturalOffer("newOffer","2021-01-20","2021-02-20",
                new java.io.File( ".\\src\\test\\resources\\offer_photo.jpg" ).getAbsolutePath(),
                "Description123");

        addCulturalOfferPage.getSaveButton().click();

        justWait(1000);

        String snackBarValue = addCulturalOfferPage.getSnackBar().getText();

        assertEquals("Cultural offer has been successfully saved.\nOk", snackBarValue);

        addCulturalOfferPage.getCulturalOfferMenu().click();
        addCulturalOfferPage.getCulturalOfferDashboardNav().click();

        for(int i = 0;i < 100; i++){
            driver.findElement(By.xpath("//*[@aria-label=\"Next page\"]")).click();
            justWait(500);
            try{
                culturalOfferDashboardPage.getCulturalOfferDeleteButton().click();
            }
            catch(NoSuchElementException e){
                continue;
            }
            break;
        }

        //culturalOfferDashboardPage.getCulturalOfferDeleteButton().click();
        justWait(1000);

        snackBarValue = culturalOfferDashboardPage.getSnackBar().getText();

        assertEquals("Cultural offer successfully deleted.\nOk", snackBarValue);
        assertEquals("http://localhost:4200/administrator/culturalOfferDashboard", driver.getCurrentUrl());
    }

    @Test
    public void addCulturalOfferTestFailNameExists() throws InterruptedException {
        mainPagePage.getCulturalOfferMenu().click();
        mainPagePage.getCulturalOfferAddNav().click();

        justWait(2000);

        addCulturalOffer("Exit","2021-01-20","2021-02-20",
                new java.io.File( ".\\src\\test\\resources\\offer_photo.jpg" ).getAbsolutePath(),
                "Description123");

        addCulturalOfferPage.getSaveButton().click();

        justWait(3000);

        String snackBarValue = addCulturalOfferPage.getSnackBar().getText();

        assertEquals("Cultural offer already exists.\nOk", snackBarValue);
        assertEquals("http://localhost:4200/administrator/addCulturalOffer", driver.getCurrentUrl());
    }

    @Test
    public void addCulturalOfferTestFailNameEmpty() throws InterruptedException {
        mainPagePage.getCulturalOfferMenu().click();
        mainPagePage.getCulturalOfferAddNav().click();

        justWait(2000);

        addCulturalOffer("","2021-01-20","2021-02-20",
                new java.io.File( ".\\src\\test\\resources\\offer_photo.jpg" ).getAbsolutePath(),
                "Description123");

        addCulturalOfferPage.getSaveButton().click();

        justWait(3000);

        String matError = addCulturalOfferPage.getError().getText();

        assertEquals("Name must be filled!", matError);
        assertEquals("http://localhost:4200/administrator/addCulturalOffer", driver.getCurrentUrl());
    }

    @Test
    public void addCulturalOfferTestFailDateInvalid() throws InterruptedException {
        mainPagePage.getCulturalOfferMenu().click();
        mainPagePage.getCulturalOfferAddNav().click();

        justWait(2000);

        addCulturalOffer("Exit2","2021-02-22","2021-02-20",
                new java.io.File( ".\\src\\test\\resources\\offer_photo.jpg" ).getAbsolutePath(),
                "Description123");

        addCulturalOfferPage.getSaveButton().click();

        justWait(3000);

        String matError = addCulturalOfferPage.getError().getText();

        assertEquals("Dates are not in order!", matError);
        assertEquals("http://localhost:4200/administrator/addCulturalOffer", driver.getCurrentUrl());
    }




}
