package com.project.tim7.e2eTests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddNewsletterPage {
    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"categorySelect\"]")
    private WebElement categorySelect;
    
    @FindBy(xpath = "//*[@id=\"subcategorySelect\"]")
    private WebElement subcategorySelect;
    
    @FindBy(xpath = "//*[@id=\"offerSelect\"]")
    private WebElement offerSelect;

    @FindBy(xpath = "//*[@id=\"name\"]")
    private WebElement name;

    @FindBy(xpath = "//*[@id=\"description\"]")
    private WebElement description;

    @FindBy(xpath = "//*[@id=\"picture\"]")
    private WebElement picture;

    @FindBy(xpath = "//*[@id=\"publish-button\"]")
    private WebElement publishButton;

    @FindBy(tagName = "simple-snack-bar")
    private WebElement snackBar;

    @FindBy(tagName = "mat-error")
    private WebElement error;

    public AddNewsletterPage() {
    }

    public AddNewsletterPage(WebDriver driver) {
        this.driver = driver;
    }

    public void ensureIsDisplayedCategorySelect() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("categorySelect")));
    }

    public void ensureIsDisplayedName() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("name")));
    }

    public WebElement getCategorySelect() {
		return categorySelect;
	}

	public void setCategorySelect(WebElement categorySelect) {
		this.categorySelect = categorySelect;
	}

	public WebElement getSubcategorySelect() {
		return subcategorySelect;
	}

	public void setSubcategorySelect(WebElement subcategorySelect) {
		this.subcategorySelect = subcategorySelect;
	}

	public WebElement getOfferSelect() {
		return offerSelect;
	}

	public void setOfferSelect(WebElement offerSelect) {
		this.offerSelect = offerSelect;
	}

	public WebElement getName() {
		return name;
	}

	public void setName(WebElement name) {
		this.name = name;
	}

	public WebElement getDescription() {
		return description;
	}

	public void setDescription(WebElement description) {
		this.description = description;
	}

	public WebElement getPicture() {
		return picture;
	}

	public void setPicture(WebElement picture) {
		this.picture = picture;
	}

	public WebElement getPublishButton() {
		return publishButton;
	}

	public void setPublishButton(WebElement publishButton) {
		this.publishButton = publishButton;
	}

	public WebElement getSnackBar() { return snackBar; }

    public WebElement getError(){return error; }
}
