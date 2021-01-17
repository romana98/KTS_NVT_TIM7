package com.project.tim7.e2eTests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UpdateNewsletterPage {
    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"name\"]")
    private WebElement name;

    @FindBy(xpath = "//*[@id=\"description\"]")
    private WebElement description;

    @FindBy(xpath = "//*[@id=\"picture\"]")
    private WebElement picture;

    @FindBy(xpath = "//*[@id=\"update-button\"]")
    private WebElement updateButton;

    @FindBy(tagName = "simple-snack-bar")
    private WebElement snackBar;

    @FindBy(tagName = "mat-error")
    private WebElement error;

    public UpdateNewsletterPage() {
    }

    public UpdateNewsletterPage(WebDriver driver) {
        this.driver = driver;
    }

    public void ensureIsDisplayedName() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("name")));
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

	public WebElement getUpdateButton() {
		return updateButton;
	}

	public void setUpdateButton(WebElement updateButton) {
		this.updateButton = updateButton;
	}

	public WebElement getSnackBar() { return snackBar; }

    public WebElement getError(){return error; }
}
