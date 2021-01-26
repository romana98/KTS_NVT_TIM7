package com.project.tim7.e2eTests.e2e;

import com.project.tim7.e2eTests.pages.AdministratorDashboardPage;
import com.project.tim7.e2eTests.pages.CulturalOfferDashboardPage;
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

public class CulturalOfferDashboardE2ETest {

    private WebDriver driver;

    private AdministratorDashboardPage administratorDashboardPage;
    private MainPagePage mainPagePage;
    private SignInPage signInPage;
    private CulturalOfferDashboardPage culturalOfferDashboardPage;


    @Before
    public void setUp() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        administratorDashboardPage = PageFactory.initElements(driver, AdministratorDashboardPage.class);
        signInPage = PageFactory.initElements(driver, SignInPage.class);
        mainPagePage = PageFactory.initElements(driver, MainPagePage.class);
        culturalOfferDashboardPage = PageFactory.initElements(driver, CulturalOfferDashboardPage.class);

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
    public void paginationNextBackTestSuccess() throws InterruptedException {
        mainPagePage.getCulturalOfferMenu().click();
        mainPagePage.getCulturalOfferDashboardNav().click();

        justWait(1000);

        culturalOfferDashboardPage.getCulturalOfferNextPageButton().click();

        justWait(1000);

        String currentPageNext = culturalOfferDashboardPage.getCurrentPage().getText();

        assertEquals("11 – 20 of 1005", currentPageNext);

        culturalOfferDashboardPage.getCulturalOfferPreviousPageButton().click();

        String currentPagePrevious = culturalOfferDashboardPage.getCurrentPage().getText();

        assertEquals("1 – 10 of 1005", currentPagePrevious);
        assertEquals("http://localhost:4200/administrator/culturalOfferDashboard", driver.getCurrentUrl());
    }
}
