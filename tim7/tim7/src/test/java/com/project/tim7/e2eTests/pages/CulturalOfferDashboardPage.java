package com.project.tim7.e2eTests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CulturalOfferDashboardPage {

    private WebDriver webDriver;

    @FindBy(xpath = "//*[@aria-label=\"Next page\"]")
    private WebElement CulturalOfferNextPageButton;

    @FindBy(xpath = "//*[@aria-label=\"Previous page\"]")
    private WebElement CulturalOfferPreviousPageButton;

    @FindBy(xpath = "//*[@id=\"1006\"]")
    private WebElement CulturalOfferDeleteButton;

    @FindBy(xpath = "//*[@id=\"3\"]")
    private WebElement CulturalOfferDeleteButton2;

    @FindBy(className = "mat-paginator-range-label")
    private WebElement currentPage;

    @FindBy(tagName = "simple-snack-bar")
    private WebElement snackBar;

    @FindBy(tagName = "mat-error")
    private WebElement error;

    @FindBy(xpath = "//*[@id=\"row3\"]")
    private WebElement clickedRow;

    public WebElement getClickedRow() {
        return clickedRow;
    }

    public WebElement getCurrentPage() {
        return currentPage;
    }

    public CulturalOfferDashboardPage(){}

    public CulturalOfferDashboardPage(WebDriver webDriver){
        this.webDriver = webDriver;
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public WebElement getCulturalOfferNextPageButton() {
        return CulturalOfferNextPageButton;
    }

    public WebElement getCulturalOfferPreviousPageButton() {
        return CulturalOfferPreviousPageButton;
    }

    public WebElement getCulturalOfferDeleteButton() {
        return CulturalOfferDeleteButton;
    }

    public WebElement getSnackBar() {
        return snackBar;
    }

    public WebElement getError() {
        return error;
    }

    public WebElement getCulturalOfferDeleteButton2() {
        return CulturalOfferDeleteButton2;
    }
}
