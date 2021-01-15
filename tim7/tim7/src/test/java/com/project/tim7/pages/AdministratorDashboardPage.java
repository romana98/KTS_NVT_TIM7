package com.project.tim7.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdministratorDashboardPage {
    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"5006\"]")
    private WebElement deleteBtn;

    public AdministratorDashboardPage() {
    }

    public AdministratorDashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    public void ensureIsNotVisibleDeleteBtn() {
        (new WebDriverWait(driver, 20)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("5006")));
    }

    public WebElement getDeleteBtn() {
        return deleteBtn;
    }

}
