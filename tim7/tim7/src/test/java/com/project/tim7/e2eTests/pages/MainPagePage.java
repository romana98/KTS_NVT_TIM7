package com.project.tim7.e2eTests.pages;

import org.hibernate.internal.build.AllowSysOut;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPagePage {

    private WebDriver driver;

    //navigation
    @FindBy(xpath = "//*[@id=\"newsletterMenu\"]")
    private WebElement newsletterMenu;

    @FindBy(xpath = "//*[@id=\"administratorMenu\"]")
    private WebElement administratorMenu;

    @FindBy(xpath = "//*[@id=\"culturalOfferMenu\"]")
    private WebElement culturalOfferMenu;

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

    @FindBy(className = "mat-paginator-range-label")
    private WebElement currentPage;

    @FindBy(xpath = "//*[@id=\"filterTypeSelect\"]")
    private WebElement filterTypeSelect;

    @FindBy(xpath = "//*[@aria-label=\"Next page\"]")
    private WebElement nextPageBtn;

    @FindBy(xpath = "//*[@aria-label=\"Previous page\"]")
    private WebElement previousPageBtn;

    @FindBy(xpath = "//*[@id=\"map\"]")
    private WebElement map;

    @FindBy(xpath = "//*[@id=\"row3\"]")
    private WebElement clickedRow;


    @FindBy(xpath = "//*[@id=\"culturaloffer-add-nav\"]")
    private WebElement culturalOfferAddNav;

    @FindBy(xpath = "//*[@id=\"culturaloffer-dashboard-nav\"]")
    private WebElement culturalOfferDashboardNav;

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

    public WebElement getNewsletterMenu() {
        return newsletterMenu;
    }

    public WebElement getAdministratorMenu() {
        return administratorMenu;
    }

    public WebElement getCulturalOfferMenu() {
        return culturalOfferMenu;
    }

    public WebElement getCurrentPage() {
        return currentPage;
    }

    public WebElement getFilterTypeSelect() {
        return filterTypeSelect;
    }

    public WebElement getNextPageBtn() {
        return nextPageBtn;
    }

    public WebElement getPreviousPageBtn() {
        return previousPageBtn;
    }

    public WebElement getMap() {
        return map;
    }

    public WebElement getClickedRow() {
        return clickedRow;
    }

    public WebElement getCulturalOfferAddNav() {
        return culturalOfferAddNav;
    }

    public WebElement getCulturalOfferDashboardNav() {
        return culturalOfferDashboardNav;
    }
}
