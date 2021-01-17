package com.project.tim7.e2eTests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CategoryDashboardPage {

    private WebDriver webDriver;

    @FindBy(xpath = "//*[@id=\"categoryNameInput\"]")
    private WebElement addCategoryNameInput;

    @FindBy(xpath = "//*[@id=\"addCategoryBtn\"]")
    private WebElement addCategoryButton;

    @FindBy(xpath = "//*[@id=\"categoryNameEdit\"]")
    private WebElement editCategoryNameInput;

    @FindBy(xpath = "//*[@id=\"updateCategoryBtn\"]")
    private WebElement editCategoryButton;

    @FindBy(xpath = "//*[@id=\"row-1\"]")
    private WebElement clickedRow;

    @FindBy(xpath = "//*[@id=\"row-2\"]")
    private WebElement clickedRowFail;

    @FindBy(tagName = "simple-snack-bar")
    private WebElement snackBar;

    @FindBy(xpath = "//*[@id=\"4\"]")
    private WebElement deleteButton;

    @FindBy(xpath = "//*[@id=\"2\"]")
    private WebElement deleteButtonFail;

    public CategoryDashboardPage(){}

    public CategoryDashboardPage(WebDriver webDriver){
        this.webDriver = webDriver;
    }

    public void ensureIsDisplayedAddCategoryName() {
        (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("categoryNameInput")));
    }

    public void ensureIsDisplayedUpdateCategoryName() {
        (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("categoryNameEdit")));
    }

    public WebElement getDeleteButtonFail(){
        return this.deleteButtonFail;
    }

    public WebElement getDeleteButton(){
        return this.deleteButton;
    }

    public WebElement getClickedRowFail(){
        return this.clickedRowFail;
    }

    public WebElement getClickedRow(){
        return this.clickedRow;
    }

    public WebElement getSnackBar(){
        return this.snackBar;
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public WebElement getAddCategoryNameInput() {
        return addCategoryNameInput;
    }

    public WebElement getAddCategoryButton() {
        return addCategoryButton;
    }

    public WebElement getEditCategoryNameInput() {
        return editCategoryNameInput;
    }

    public WebElement getEditCategoryButton() {
        return editCategoryButton;
    }

}
