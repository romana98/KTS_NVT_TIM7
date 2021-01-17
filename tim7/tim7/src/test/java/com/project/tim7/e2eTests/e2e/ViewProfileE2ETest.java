package com.project.tim7.e2eTests.e2e;

import com.project.tim7.e2eTests.pages.MainPagePage;
import com.project.tim7.e2eTests.pages.SignInPage;
import com.project.tim7.e2eTests.pages.ViewProfilePage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewProfileE2ETest {

    private WebDriver driver;

    private ViewProfilePage viewProfilePage;
    private MainPagePage mainPagePage;
    private SignInPage signInPage;


    @Before
    public void setUp() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        viewProfilePage = PageFactory.initElements(driver, ViewProfilePage.class);
        signInPage = PageFactory.initElements(driver, SignInPage.class);
        mainPagePage = PageFactory.initElements(driver, MainPagePage.class);

        //sign in
        driver.get("http://localhost:4200/sign-in");
        signInPage.getUsername().sendKeys("micoR");
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
    public void viewProfileSuccess() throws InterruptedException {

        mainPagePage.getViewProfileNav().click();

        justWait(1000);

        viewProfilePage.ensureIsDisplayedUsername();
        viewProfilePage.ensureIsDisplayedEmail();

        viewProfilePage.getEditProfileBtn().click();

        justWait(1000);

        viewProfilePage.ensureIsNotVisibleEditBtn();


        assertEquals("http://localhost:4200/registered/edit-profile", driver.getCurrentUrl());
    }
}
