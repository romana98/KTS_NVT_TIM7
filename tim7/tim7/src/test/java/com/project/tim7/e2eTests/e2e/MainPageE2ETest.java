package com.project.tim7.e2eTests.e2e;

import com.project.tim7.e2eTests.pages.AddAdministratorPage;
import com.project.tim7.e2eTests.pages.AdministratorDashboardPage;
import com.project.tim7.e2eTests.pages.MainPagePage;
import com.project.tim7.e2eTests.pages.SignInPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainPageE2ETest {

    private WebDriver driver;

    private MainPagePage mainPagePage;
    private SignInPage signInPage;


    @Before
    public void setUp() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        signInPage = PageFactory.initElements(driver, SignInPage.class);
        mainPagePage = PageFactory.initElements(driver, MainPagePage.class);

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

    @Test
    public void paginationNextBackTestSuccess() throws InterruptedException {

        mainPagePage.getNextPageBtn().click();

        String currentPageNext = mainPagePage.getCurrentPage().getText();

        assertEquals("11 – 20 of 1005", currentPageNext);


        mainPagePage.getPreviousPageBtn().click();

        String currentPagePrevious = mainPagePage.getCurrentPage().getText();

        assertEquals("1 – 10 of 1005", currentPagePrevious);
        assertEquals("http://localhost:4200/", driver.getCurrentUrl());
    }

    //Filter by everything with 'manifestacija' - 8 results expected
    @Test
    public void filterByAll() throws InterruptedException {

        mainPagePage.getFilterInput().sendKeys("manifestacija");

        justWait(2000);

        String currentPageNext = mainPagePage.getCurrentPage().getText();

        assertEquals("1 – 8 of 8", currentPageNext);

        assertEquals("http://localhost:4200/", driver.getCurrentUrl());
    }

    //Filter by everything without results.
    @Test
    public void filterByAllNoResults() throws InterruptedException {

        mainPagePage.getFilterInput().sendKeys("manifestacijae");

        justWait(2000);

        String currentPageNext = mainPagePage.getCurrentPage().getText();

        assertEquals("0 of 0", currentPageNext);

        assertEquals("http://localhost:4200/", driver.getCurrentUrl());
    }

    //Filter by location with value 'belgrade' - 2 values expected.
    @Test
    public void filterByLocation() throws InterruptedException {

        mainPagePage.getFilterInput().sendKeys("belgrade");
        mainPagePage.getFilterTypeSelect().sendKeys("Location");

        justWait(2000);

        String currentPageNext = mainPagePage.getCurrentPage().getText();

        assertEquals("1 – 2 of 2", currentPageNext);

        assertEquals("http://localhost:4200/", driver.getCurrentUrl());
    }

    //Filter by Name with param 'exit - 1 value expected.
    @Test
    public void filterByName() throws InterruptedException {

        mainPagePage.getFilterInput().sendKeys("exit");
        mainPagePage.getFilterTypeSelect().sendKeys("Name");

        justWait(2000);

        String currentPageNext = mainPagePage.getCurrentPage().getText();

        assertEquals("1 – 1 of 1", currentPageNext);

        assertEquals("http://localhost:4200/", driver.getCurrentUrl());
    }

    //Filter by category with value 'institucija' - expecting 22 results.
    @Test
    public void filterByCategory() throws InterruptedException {

        mainPagePage.getFilterInput().sendKeys("institucija");
        mainPagePage.getFilterTypeSelect().sendKeys("Category");

        justWait(2000);

        String currentPageNext = mainPagePage.getCurrentPage().getText();

        assertEquals("1 – 10 of 22", currentPageNext);

        assertEquals("http://localhost:4200/", driver.getCurrentUrl());
    }

    //Filter by subcategory with value 'bioskop' - expecting 4 results.
    @Test
    public void filterBySubcategory() throws InterruptedException {

        mainPagePage.getFilterInput().sendKeys("bioskop");
        mainPagePage.getFilterTypeSelect().sendKeys("Subcategory");

        justWait(2000);

        String currentPageNext = mainPagePage.getCurrentPage().getText();

        assertEquals("1 – 4 of 4", currentPageNext);

        assertEquals("http://localhost:4200/", driver.getCurrentUrl());
    }

    //Selecting cultural offer from table and checking marker on map.
    @Test
    public void mapMarkerSelect() throws InterruptedException {

        mainPagePage.getClickedRow().click();

        justWait(2000);

        assertEquals("46.100376", mainPagePage.getMap().getAttribute("ng-reflect-latitude"));
        assertEquals("19.667587", mainPagePage.getMap().getAttribute("ng-reflect-longitude"));

        assertEquals("http://localhost:4200/", driver.getCurrentUrl());
    }

}
