package com.project.tim7.e2eTests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DetailedCulturalOfferPage {

    private WebDriver webDriver;

    @FindBy(xpath = "//*[@id=\"subscribeButton\"]")
    private WebElement subscribeButton;
    @FindBy(xpath = "//*[@id=\"unsubscribeButton\"]")
    private WebElement unsubscribeButton;

    @FindBy(xpath = "//*[@id=\"carouselCulturalOffer\"]/mat-carousel/div/button/span/mat-icon[text() = \"arrow_back\" ]")
    private WebElement carouselOfferBefore;
    @FindBy(xpath = "//*[@id=\"carouselCulturalOffer\"]/mat-carousel/div/button/span/mat-icon[text() = \"arrow_forward\" ]")
    private WebElement carouselOfferNext;
    @FindBy(xpath = "//*[@id=\"carouselCulturalOffer\"]/mat-carousel/div/ul")
    private WebElement carouselOfferSlide;

    @FindBy(xpath = "//*[@id=\"carouselCommentPictures\"]/mat-carousel/div/button/span/mat-icon[text() = \"arrow_back\" ]")
    private WebElement carouselCommentBefore;
    @FindBy(xpath = "//*[@id=\"carouselCommentPictures\"]/mat-carousel/div/button/span/mat-icon[text() = \"arrow_forward\" ]")
    private WebElement carouselCommentNext;
    @FindBy(xpath = "//*[@id=\"carouselCommentPictures\"]/mat-carousel/div/ul")
    private WebElement carouselCommentSlide;
    @FindBy(xpath = "//*[@id=\"commentNavigateBefore\"]")
    private WebElement navigateCommentBefore;
    @FindBy(xpath = "//*[@id=\"commentNavigateNext\"]")
    private WebElement navigateCommentAfter;
    @FindBy(xpath = "//*[@id=\"commentDescription\"]")
    private WebElement commentDescription;

    @FindBy(xpath = "//*[@id=\"newsletterNavigateBefore\"]")
    private WebElement navigateNewsletterBefore;
    @FindBy(xpath = "//*[@id=\"newsletterNavigateNext\"]")
    private WebElement navigateNewsletterAfter;
    @FindBy(xpath = "//*[@id=\"newsletterDescription\"]")
    private WebElement newsletterDescription;

    @FindBy(xpath = "//*[@id=\"starRating\"]")
    private WebElement rateStar;
    @FindBy(xpath = "//*[@id=\"addRateButton\"]")
    private WebElement rateButton;

    @FindBy(xpath = "//*[@id=\"commentInput\"]")
    private WebElement commentInput;
    @FindBy(xpath = "//*[@id=\"inputPictureButton\"]")
    private WebElement pictureInput;
    @FindBy(xpath = "//*[@id=\"addCommentButton\"]")
    private WebElement addCommentButton;
    @FindBy(xpath = "//*[@id=\"publish-newsletter-button\"]")
    private WebElement publishNewsletterButton;

    @FindBy(tagName = "simple-snack-bar")
    private WebElement snackBar;

    public WebElement getAddCommentButton(){
        return addCommentButton;
    }

    public WebElement getCommentInput() {
        return commentInput;
    }

    public WebElement getPictureInput() {
        return pictureInput;
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public WebElement getRateStar() {
        return rateStar;
    }

    public WebElement getRateButton() {
        return rateButton;
    }

    public WebElement getNavigateNewsletterBefore() {
        return navigateNewsletterBefore;
    }

    public WebElement getNavigateNewsletterAfter() {
        return navigateNewsletterAfter;
    }

    public WebElement getNewsletterDescription() {
        return newsletterDescription;
    }

    public WebElement getSnackBar(){
        return snackBar;
    }

    public WebElement getNavigateCommentBefore(){
        return navigateCommentBefore;
    }

    public WebElement getNavigateCommentAfter(){
        return navigateCommentAfter;
    }

    public WebElement getCommentDescription(){
        return commentDescription;
    }

    public WebElement getSubscribeButton() {
        return subscribeButton;
    }

    public WebElement getUnsubscribeButton() {
        return unsubscribeButton;
    }

    public WebElement getCarouselOfferBefore() {
        return carouselOfferBefore;
    }

    public WebElement getCarouselOfferNext() {
        return carouselOfferNext;
    }

    public WebElement getCarouselOfferSlide() {
        return carouselOfferSlide;
    }

    public WebElement getCarouselCommentBefore() {
        return carouselCommentBefore;
    }

    public WebElement getCarouselCommentNext() {
        return carouselCommentNext;
    }

    public WebElement getCarouselCommentSlide() {
        return carouselCommentSlide;
    }

	public WebElement getPublishNewsletterButton() {
		return publishNewsletterButton;
	}
}
