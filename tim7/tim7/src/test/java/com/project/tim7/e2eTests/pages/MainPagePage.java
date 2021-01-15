package com.project.tim7.e2eTests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPagePage {

    private WebDriver driver;

    //navigation
    @FindBy(xpath = "//*[@id=\"add-admin-navigate\"]")
    private WebElement addAdminNav;

    @FindBy(xpath = "//*[@id=\"admin-dashboard-navigate\"]")
    private WebElement adminDashboardNav;

    @FindBy(xpath = "//*[@id=\"view-profile\"]")
    private WebElement viewProfileNav;

    public MainPagePage() {
    }

    public MainPagePage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getAddAdminNav() {
        return addAdminNav;
    }

    public WebElement getAdminDashboardNav() {
        return adminDashboardNav;
    }

    public WebElement getViewProfileNav() {
        return viewProfileNav;
    }
}
