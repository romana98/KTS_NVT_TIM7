package com.project.tim7.e2eTests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NewsletterDashboardPage {
    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"1001\"]")
    private WebElement deleteBtn;
    
    @FindBy(xpath = "//*[@id=\"row1000\"]")
    private WebElement clickedRow;

    @FindBy(tagName = "simple-snack-bar")
    private WebElement snackBar;

    @FindBy(xpath = "//*[@aria-label=\"Next page\"]")
    private WebElement nextPageBtn;

    @FindBy(xpath = "//*[@aria-label=\"Previous page\"]")
    private WebElement previousPageBtn;

    @FindBy(className = "mat-paginator-range-label")
    private WebElement currentPage;

    public NewsletterDashboardPage() {
    }

    public NewsletterDashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    public void ensureIsNotVisibleDeleteBtn() {
        (new WebDriverWait(driver, 20)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("1001")));
    }
    
    public void ensureVisibleDeleteBtn() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("1001")));
    }

    public WebElement getDeleteBtn() {
        return deleteBtn;
    }
    
    public WebElement getClickedRow() {
        return clickedRow;
    }

    public WebElement getSnackBar() { return snackBar; }

    public WebElement getNextPageBtn(){ return nextPageBtn; }

    public WebElement getPreviousPageBtn(){ return previousPageBtn; }

    public WebElement getCurrentPage(){return  currentPage; }

}
