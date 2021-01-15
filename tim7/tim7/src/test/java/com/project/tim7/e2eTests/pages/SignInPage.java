package com.project.tim7.e2eTests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignInPage {

    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"username\"]")
    private WebElement username;

    @FindBy(xpath = "//*[@id=\"password\"]")
    private WebElement password;

    @FindBy(xpath = "//*[@id=\"sign-in-button\"]")
    private WebElement signInBtn;

    public SignInPage() {
    }

    public SignInPage(WebDriver driver) {
        this.driver = driver;
    }

    public void ensureIsDisplayedUsername() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("username")));
    }
    public void ensureIsDisplayedPassword() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("password")));
    }
    public void ensureIsNotVisibleSignInBtn() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("sign-in-button")));
    }


    public void ensureIsNotVisibleUsername() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("username")));
    }
    public void ensureIsNotVisiblePassowrd() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("password")));
    }

    public WebElement getUsername() {
        return username;
    }

    public WebElement getPassword() {
        return password;
    }

    public WebElement getSignInBtn() {
        return signInBtn;
    }
}
