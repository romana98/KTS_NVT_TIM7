package com.project.tim7.e2eTests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddAdministratorPage {
    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"username\"]")
    private WebElement username;

    @FindBy(xpath = "//*[@id=\"email\"]")
    private WebElement email;

    @FindBy(xpath = "//*[@id=\"password\"]")
    private WebElement password;

    @FindBy(xpath = "//*[@id=\"passwordConfirm\"]")
    private WebElement passwordConfirm;

    @FindBy(xpath = "//*[@id=\"add-admin-button\"]")
    private WebElement addAdminBtn;

    @FindBy(tagName = "simple-snack-bar")
    private WebElement snackBar;

    @FindBy(tagName = "mat-error")
    private WebElement error;

    @FindBy(xpath = "//*[@id=\"admin-dashboard-navigate\"]")
    private WebElement adminDashboardNavigate;

    public AddAdministratorPage() {
    }

    public AddAdministratorPage(WebDriver driver) {
        this.driver = driver;
    }

    public void ensureIsDisplayedUsername() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("username")));
    }

    public void ensureIsDisplayedEmail() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("email")));
    }

    public WebElement getAdminDashboardNavigate(){
        return adminDashboardNavigate;
    }

    public WebElement getUsername() {
        return username;
    }

    public WebElement getEmail() {
        return email;
    }

    public WebElement getPassword() {
        return password;
    }

    public WebElement getPasswordConfirm() {
        return passwordConfirm;
    }

    public WebElement getAddAdminBtn() {
        return addAdminBtn;
    }

    public WebElement getSnackBar() { return snackBar; }

    public WebElement getError(){return error; }
}
