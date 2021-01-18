package com.project.tim7.e2eTests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NewsletterPage {
    private WebDriver driver;

    @FindBy(tagName = "simple-snack-bar")
    private WebElement snackBar;
    
    @FindBy(xpath = "//*[@id=\"mat-tab-label-0-1\"]")
    private WebElement categoryTab;
    
    @FindBy(xpath = "//*[@id=\"details629\"]")
    private WebElement detailsButton;
    
    @FindBy(xpath = "//*[@id=\"unsubscribe629\"]")
    private WebElement unsubscribeButton;
    
    @FindBy(xpath = "//*[@id=\"mat-dialog-title-0\"]")
    private WebElement dialogTitle;

    public NewsletterPage() {
    }

    public NewsletterPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getSnackBar() { return snackBar; }

    public WebElement getCategoryTab() { return categoryTab; }
    
    public WebElement getDialogTitle() { return dialogTitle; }
    
    public WebElement getDetailsButton() { return detailsButton; }
    
    public WebElement getUnsubscribeButton() { return unsubscribeButton; }

}
