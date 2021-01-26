package com.project.tim7.e2eTests.e2e;

import com.project.tim7.e2eTests.pages.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.io.File;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdateCulturalOfferE2ETest {

    private WebDriver driver;

    @Autowired
    private TestRestTemplate restTemplate;

    private MainPagePage mainPagePage;
    private SignInPage signInPage;
    private CulturalOfferDashboardPage culturalOfferDashboardPage;
    private UpdateCulturalOfferPage updateCulturalOfferPage;

    @Before
    public void setUp() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        signInPage = PageFactory.initElements(driver, SignInPage.class);
        mainPagePage = PageFactory.initElements(driver, MainPagePage.class);
        culturalOfferDashboardPage = PageFactory.initElements(driver, CulturalOfferDashboardPage.class);
        updateCulturalOfferPage = PageFactory.initElements(driver, UpdateCulturalOfferPage.class);
        //sign in
        driver.get("http://localhost:4200/sign-in");
        signInPage.getUsername().sendKeys("mico");
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

    private void updateCulturalOffer(String name, String dateStart, String dateEnd, String picture, String description){
        updateCulturalOfferPage.getNameField().clear();
        updateCulturalOfferPage.getNameField().sendKeys(name);

        updateCulturalOfferPage.getPictureField().sendKeys(picture);

        updateCulturalOfferPage.getDescriptionField().clear();
        updateCulturalOfferPage.getDescriptionField().sendKeys(description);

    }

    @Test
    public void updateCulturalOfferTestSuccess() throws InterruptedException {
        mainPagePage.getCulturalOfferMenu().click();
        mainPagePage.getCulturalOfferDashboardNav().click();

        justWait(2000);

        culturalOfferDashboardPage.getClickedRow().click();

        justWait(1000);

        updateCulturalOffer("newOffer","2021-01-20","2021-02-20",
                new File( ".\\src\\test\\resources\\offer_photo.jpg" ).getAbsolutePath(),
                "Description123");

        justWait(2000);

        updateCulturalOfferPage.getSaveButton().click();

        justWait(1000);

        String snackBarValue = updateCulturalOfferPage.getSnackBar().getText();

        assertEquals("Cultural offer saved successfully.\nOk", snackBarValue);

        updateCulturalOfferPage.getCulturalOfferMenu().click();
        updateCulturalOfferPage.getCulturalOfferDashboardNav().click();

        for(int i = 0;i < 100; i++){
            driver.findElement(By.xpath("//*[@aria-label=\"Next page\"]")).click();
            justWait(300);
            try{
                culturalOfferDashboardPage.getClickedRow().click();
            }
            catch(NoSuchElementException e){
                continue;
            }
            break;
        }

        justWait(1000);

        updateCulturalOffer("Ozaloscena porodica: Branislav Nusic","2019-02-11","2019-02-18",
                new File( ".\\src\\test\\resources\\offer_photo.jpg" ).getAbsolutePath(),
                "Po motivima Branislava Nusica.");

        updateCulturalOfferPage.getSaveButton().click();

        justWait(1000);

        snackBarValue = culturalOfferDashboardPage.getSnackBar().getText();

        assertEquals("Cultural offer saved successfully.\nOk", snackBarValue);
        assertEquals("http://localhost:4200/administrator/editCulturalOffer;id=3", driver.getCurrentUrl());
    }

    @Test
    public void addCulturalOfferTestFailNameExists() throws InterruptedException {
        mainPagePage.getCulturalOfferMenu().click();
        mainPagePage.getCulturalOfferDashboardNav().click();

        justWait(2000);

        culturalOfferDashboardPage.getClickedRow2().click();

        justWait(1000);

        updateCulturalOffer("Exit","2021-01-20","2021-02-20",
                new File( ".\\src\\test\\resources\\offer_photo.jpg" ).getAbsolutePath(),
                "Description123");

        justWait(2000);

        updateCulturalOfferPage.getSaveButton().click();

        justWait(1000);

        String snackBarValue = updateCulturalOfferPage.getSnackBar().getText();

        assertEquals("Cultural offer already exists.\nOk", snackBarValue);
        assertEquals("http://localhost:4200/administrator/editCulturalOffer;id=4", driver.getCurrentUrl());
    }

    @Test
    public void addCulturalOfferTestFailNameEmpty() throws InterruptedException {
        mainPagePage.getCulturalOfferMenu().click();
        mainPagePage.getCulturalOfferDashboardNav().click();

        justWait(2000);

        culturalOfferDashboardPage.getClickedRow().click();

        justWait(1000);

        updateCulturalOffer("","2021-01-20","2021-02-20",
                new File( ".\\src\\test\\resources\\offer_photo.jpg" ).getAbsolutePath(),
                "Description123");

        justWait(2000);

        updateCulturalOfferPage.getSaveButton().click();

        justWait(1000);


        String matError = updateCulturalOfferPage.getError().getText();

        assertEquals("Name must be filled!", matError);
        assertEquals("http://localhost:4200/administrator/editCulturalOffer;id=3", driver.getCurrentUrl());
    }

}
