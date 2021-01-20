package com.project.tim7.e2eTests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SubcategoryDashboardPage {

    private WebDriver webDriver;

    @FindBy(xpath = "//*[@id=\"categoryNameSelect\"]")
    private WebElement categoryNameSelect;

    @FindBy(xpath = "//*[@id=\"subcategoryNameInput\"]")
    private WebElement subcategoryNameInput;

    @FindBy(xpath = "//*[@id=\"addSubcategoryButton\"]")
    private WebElement addSubcategoryButton;

    @FindBy(xpath = "//*[@id=\"categoryName\"]")
    private WebElement categoryName;

    @FindBy(xpath = "//*[@id=\"subcategoryNameEdit\"]")
    private WebElement subcategoryNameEdit;

    @FindBy(xpath = "//*[@id=\"updateSubcategoryButton\"]")
    private WebElement updateSubcategoryButton;

    @FindBy(xpath = "//*[@id=\"row1\"]")
    private WebElement clickedRow;

    @FindBy(xpath = "//*[@id=\"row2\"]")
    private WebElement clickedRowFail;

    @FindBy(tagName = "simple-snack-bar")
    private WebElement snackBar;

    @FindBy(xpath = "//*[@id=\"1016\"]")
    private WebElement deleteButton;

    @FindBy(xpath = "//*[@id=\"2\"]")
    private WebElement deleteButtonFail;

    public SubcategoryDashboardPage(){}

    public SubcategoryDashboardPage(WebDriver webDriver){
        this.webDriver = webDriver;
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public WebElement getCategoryNameSelect() {
        return categoryNameSelect;
    }

    public WebElement getSubcategoryNameInput() {
        return subcategoryNameInput;
    }

    public WebElement getAddSubcategoryButton() {
        return addSubcategoryButton;
    }

    public WebElement getCategoryName() {
        return categoryName;
    }

    public WebElement getSubcategoryNameEdit() {
        return subcategoryNameEdit;
    }

    public WebElement getUpdateSubcategoryButton() {
        return updateSubcategoryButton;
    }

    public WebElement getClickedRow() {
        return clickedRow;
    }

    public WebElement getClickedRowFail() {
        return clickedRowFail;
    }

    public WebElement getSnackBar() {
        return snackBar;
    }

    public WebElement getDeleteButton() {
        return deleteButton;
    }

    public WebElement getDeleteButtonFail() {
        return deleteButtonFail;
    }

    public void ensureIsDisplayedAddSubcategoryName() {
        (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("subcategoryNameInput")));
    }

    public void ensureIsDisplayedSelectCategory() {
        (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("categoryNameSelect")));
    }

    public void ensureIsDisplayedUpdateSubcategoryName() {
        (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("subcategoryNameEdit")));
    }
}
