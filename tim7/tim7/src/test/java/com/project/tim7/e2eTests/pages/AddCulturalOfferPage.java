package com.project.tim7.e2eTests.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddCulturalOfferPage {

    @FindBy(xpath = "//*[@id=\"name\"]")
    private WebElement nameField;

    @FindBy(xpath = "//*[@id=\"startDate\"]")
    private WebElement startDateField;

    @FindBy(xpath = "//*[@id=\"endDate\"]")
    private WebElement endDateField;

    @FindBy(xpath = "//*[@id=\"category\"]")
    private WebElement categoryField;

    @FindBy(xpath = "//*[@id=\"subcategory\"]")
    private WebElement subcategoryField;

    @FindBy(xpath = "//*[@id=\"description\"]")
    private WebElement descriptionField;

    @FindBy(xpath = "//*[@id=\"file\"]")
    private WebElement pictureField;

    @FindBy(xpath = "//*[@id=\"deleteButton\"]")
    private WebElement deleteButton;

    @FindBy(xpath = "//*[@id=\"saveButton\"]")
    private WebElement saveButton;

    @FindBy(tagName = "simple-snack-bar")
    private WebElement snackBar;

    @FindBy(tagName = "mat-error")
    private WebElement error;

    @FindBy(xpath = "//*[@id=\"culturalOfferMenu\"]")
    private WebElement culturalOfferMenu;

    @FindBy(xpath = "//*[@id=\"culturaloffer-dashboard-nav\"]")
    private WebElement culturalOfferDashboardNav;

    public WebElement getNameField() {
        return nameField;
    }

    public WebElement getStartDateField() {
        return startDateField;
    }

    public WebElement getEndDateField() {
        return endDateField;
    }

    public WebElement getCategoryField() {
        return categoryField;
    }

    public WebElement getSubcategoryField() {
        return subcategoryField;
    }

    public WebElement getDescriptionField() {
        return descriptionField;
    }

    public WebElement getPictureField() {
        return pictureField;
    }

    public WebElement getDeleteButton() {
        return deleteButton;
    }

    public WebElement getSaveButton() {
        return saveButton;
    }

    public WebElement getSnackBar() {
        return snackBar;
    }

    public WebElement getError() {
        return error;
    }

    public WebElement getCulturalOfferMenu() {
        return culturalOfferMenu;
    }

    public WebElement getCulturalOfferDashboardNav() {
        return culturalOfferDashboardNav;
    }
}
