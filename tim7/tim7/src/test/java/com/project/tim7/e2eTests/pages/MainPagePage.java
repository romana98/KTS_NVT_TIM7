package com.project.tim7.e2eTests.pages;

import org.hibernate.internal.build.AllowSysOut;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPagePage {

    private WebDriver driver;

    //navigation
    @FindBy(xpath = "//*[@id=\"add-admin-navigate\"]")
    private WebElement addAdminNav;

    @FindBy(xpath = "//*[@id=\"admin-dashboard-navigate\"]")
    private WebElement adminDashboardNav;

    @FindBy(xpath = "//*[@id=\"view-profile\"]")
    private WebElement viewProfileNav;

    @FindBy(xpath = "//*[@id=\"category-dashboard-nav\"]")
    private WebElement manageCategoriesNav;

    @FindBy(xpath = "//*[@id=\"subcategory-dashboard-nav\"]")
    private WebElement manageSubcategoriesNav;
    
    @FindBy(xpath = "//*[@id=\"publish-newsletter-nav\"]")
    private WebElement publishNewsletterNav;
    
    @FindBy(xpath = "//*[@id=\"newsletter-dashboard-nav\"]")
    private WebElement newsletterDashboardNav;
    
    @FindBy(xpath = "//*[@id=\"newsletter-subscribed-nav\"]")
    private WebElement newsletterSubscribedNav;
    
    @FindBy(xpath = "//*[@id=\"home-nav\"]")
    private WebElement homeNav;

    @FindBy(xpath = "//*[@id=\"row2\"]")
    private WebElement detailedCulturalOfferRow;
    
    @FindBy(xpath = "//*[@id=\"filter\"]")
    private WebElement filterInput;
    
    @FindBy(xpath = "//*[@id=\"row834\"]")
    private WebElement offerToSubscribe;

    public MainPagePage() {
    }

    public MainPagePage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getDetailedCulturalOfferRow(){
        return this.detailedCulturalOfferRow;
    }

    public WebElement getManageSubcategoriesNav(){
        return manageSubcategoriesNav;
    }

    public WebElement getManageCategoriesNav(){
        return manageCategoriesNav;
    }

    public WebElement getAddAdminNav() {
        return addAdminNav;
    }

    public WebElement getAdminDashboardNav() {
        return adminDashboardNav;
    }

    public WebElement getViewProfileNav() {
        return viewProfileNav;
    }

	public WebElement getPublishNewsletterNav() {
		return publishNewsletterNav;
	}
	
	public WebElement getNewsletterDashboardNav() {
		return newsletterDashboardNav;
	}
	
	public WebElement getNewsletterSubscribedNav() {
		return newsletterSubscribedNav;
	}
	
	public WebElement getHomeNav() {
		return homeNav;
	}
	
	public WebElement getFilterInput() {
		return filterInput;
	}
	
	public WebElement getOfferToSubscribe() {
		return offerToSubscribe;
	}
}
