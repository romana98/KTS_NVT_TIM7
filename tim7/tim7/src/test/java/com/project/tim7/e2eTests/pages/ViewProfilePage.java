package com.project.tim7.e2eTests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ViewProfilePage {

    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"username\"]")
    private WebElement username;

    @FindBy(xpath = "//*[@id=\"email\"]")
    private WebElement email;

    @FindBy(xpath = "//*[@id=\"edit-profile-button\"]")
    private WebElement editProfileBtn;

    public ViewProfilePage() {
    }

    public ViewProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    public void ensureIsDisplayedUsername() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("username")));
    }

    public void ensureIsDisplayedEmail() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("email")));
    }
    public void ensureIsNotVisibleEditBtn() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("edit-profile-button")));
    }

    public WebElement getUsername() {
        return username;
    }

    public WebElement getEmail() {
        return email;
    }

    public WebElement getEditProfileBtn() {
        return editProfileBtn;
    }
}
