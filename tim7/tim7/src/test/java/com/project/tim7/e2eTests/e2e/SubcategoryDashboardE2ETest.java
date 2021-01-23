package com.project.tim7.e2eTests.e2e;

import com.project.tim7.e2eTests.pages.CategoryDashboardPage;
import com.project.tim7.e2eTests.pages.MainPagePage;
import com.project.tim7.e2eTests.pages.SignInPage;
import com.project.tim7.e2eTests.pages.SubcategoryDashboardPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubcategoryDashboardE2ETest {

    private WebDriver driver;

    private SignInPage signInPage;
    private MainPagePage mainPagePage;
    private SubcategoryDashboardPage subcategoryDashboardPage;

    @Before
    public void setUp() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        subcategoryDashboardPage = PageFactory.initElements(driver, SubcategoryDashboardPage.class);
        signInPage = PageFactory.initElements(driver, SignInPage.class);
        mainPagePage = PageFactory.initElements(driver, MainPagePage.class);

        //sign in
        driver.get("http://localhost:4200/sign-in");
        // logging in as administrator
        signInPage.getUsername().sendKeys("mico");
        signInPage.getPassword().sendKeys("123qweASD");
        signInPage.getSignInBtn().click();
        justWait(5000);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    private void justWait(Integer howLong) throws InterruptedException {
        synchronized (driver)
        {
            driver.wait(howLong);
        }
    }

    @Test
    public void testAddSubcategorySuccess() throws InterruptedException {

        mainPagePage.getManageSubcategoriesNav().click();

        justWait(1000);

        subcategoryDashboardPage.ensureIsDisplayedAddSubcategoryName();
        subcategoryDashboardPage.ensureIsDisplayedSelectCategory();

        subcategoryDashboardPage.getCategoryNameSelect().sendKeys("Manifestacija");
        subcategoryDashboardPage.getSubcategoryNameInput().sendKeys("New subcategory");
        subcategoryDashboardPage.getAddSubcategoryButton().click();

        justWait(1000);

        String snackBarText = subcategoryDashboardPage.getSnackBar().getText();

        assertEquals("Subcategory added successfully.\nOk", snackBarText);
        assertEquals("http://localhost:4200/administrator/manage-subcategories/dashboard", driver.getCurrentUrl());

        for(int i = 0;i < 101; i++){
            driver.findElement(By.xpath("//*[@aria-label=\"Next page\"]")).click();
        }

        subcategoryDashboardPage.getDeleteButton().click();
        justWait(1000);

        snackBarText = subcategoryDashboardPage.getSnackBar().getText();

        assertEquals("Subcategory deleted successfully.\nOk", snackBarText);
        assertEquals("http://localhost:4200/administrator/manage-subcategories/dashboard", driver.getCurrentUrl());

    }

    @Test
    public void testAddSubcategoryFail() throws InterruptedException {

        mainPagePage.getManageSubcategoriesNav().click();

        justWait(1000);

        subcategoryDashboardPage.ensureIsDisplayedAddSubcategoryName();
        subcategoryDashboardPage.ensureIsDisplayedSelectCategory();

        subcategoryDashboardPage.getCategoryNameSelect().sendKeys("Manifestacija");
        subcategoryDashboardPage.getSubcategoryNameInput().sendKeys("Karneval");
        justWait(2000);
        subcategoryDashboardPage.getAddSubcategoryButton().click();

        justWait(2000);

        String snackBarText = subcategoryDashboardPage.getSnackBar().getText();

        assertEquals("Subcategory with that name already exists!\nOk", snackBarText);
        assertEquals("http://localhost:4200/administrator/manage-subcategories/dashboard", driver.getCurrentUrl());

    }

    @Test
    public void testUpdateSubcategorySuccess() throws InterruptedException {
        mainPagePage.getManageSubcategoriesNav().click();

        justWait(1000);

        subcategoryDashboardPage.ensureIsDisplayedAddSubcategoryName();
        subcategoryDashboardPage.ensureIsDisplayedSelectCategory();
        subcategoryDashboardPage.getClickedRow().click();

        justWait(1000);

        subcategoryDashboardPage.ensureIsDisplayedUpdateSubcategoryName();
        subcategoryDashboardPage.getSubcategoryNameEdit().clear();
        subcategoryDashboardPage.getSubcategoryNameEdit().sendKeys("New subcategory edit");
        subcategoryDashboardPage.getUpdateSubcategoryButton().click();

        justWait(1000);

        String snackBarText = subcategoryDashboardPage.getSnackBar().getText();

        assertEquals("Subcategory updated successfully.\nOk", snackBarText);
        assertEquals("http://localhost:4200/administrator/manage-subcategories/dashboard", driver.getCurrentUrl());

        for(int i = 0;i < 101; i++){
            driver.findElement(By.xpath("//*[@aria-label=\"Next page\"]")).click();
        }

        subcategoryDashboardPage.ensureIsDisplayedAddSubcategoryName();
        subcategoryDashboardPage.ensureIsDisplayedSelectCategory();
        subcategoryDashboardPage.getClickedRow().click();

        justWait(1000);

        subcategoryDashboardPage.ensureIsDisplayedUpdateSubcategoryName();
        subcategoryDashboardPage.getSubcategoryNameEdit().clear();
        subcategoryDashboardPage.getSubcategoryNameEdit().sendKeys("Muzej");
        subcategoryDashboardPage.getUpdateSubcategoryButton().click();

        justWait(1000);

        snackBarText = subcategoryDashboardPage.getSnackBar().getText();

        assertEquals("Subcategory updated successfully.\nOk", snackBarText);
        assertEquals("http://localhost:4200/administrator/manage-subcategories/dashboard", driver.getCurrentUrl());

    }

    @Test
    public void testUpdateCategoryFail() throws InterruptedException {
        mainPagePage.getManageSubcategoriesNav().click();

        justWait(1000);

        subcategoryDashboardPage.ensureIsDisplayedAddSubcategoryName();
        subcategoryDashboardPage.ensureIsDisplayedSelectCategory();
        subcategoryDashboardPage.getClickedRowFail().click();

        justWait(1000);

        subcategoryDashboardPage.ensureIsDisplayedUpdateSubcategoryName();
        subcategoryDashboardPage.getSubcategoryNameEdit().clear();
        subcategoryDashboardPage.getSubcategoryNameEdit().sendKeys("Karneval");
        subcategoryDashboardPage.getUpdateSubcategoryButton().click();

        justWait(1000);

        String snackBarText = subcategoryDashboardPage.getSnackBar().getText();

        assertEquals("Subcategory with that name already exists!\nOk", snackBarText);
        assertEquals("http://localhost:4200/administrator/manage-subcategories/dashboard", driver.getCurrentUrl());
    }

    @Test
    public void testDeleteFail() throws InterruptedException {

        mainPagePage.getManageSubcategoriesNav().click();

        justWait(1000);

        subcategoryDashboardPage.ensureIsDisplayedSelectCategory();
        subcategoryDashboardPage.ensureIsDisplayedAddSubcategoryName();
        subcategoryDashboardPage.getDeleteButtonFail().click();

        justWait(1000);

        String snackBarText = subcategoryDashboardPage.getSnackBar().getText();

        assertEquals("Deleting failed! Check if selected subcategory has its cultural offers!\nOk", snackBarText);
        assertEquals("http://localhost:4200/administrator/manage-subcategories/dashboard", driver.getCurrentUrl());

    }

    @Test
    public void paginationNextBackTestSuccess() throws InterruptedException {

        mainPagePage.getManageSubcategoriesNav().click();

        justWait(1000);

        driver.findElement(By.xpath("//*[@aria-label=\"Next page\"]")).click();

        String currentPageNext = driver.findElement(By.className("mat-paginator-range-label")).getText();

        assertEquals("11 – 20 of 1015", currentPageNext);



        driver.findElement(By.xpath("//*[@aria-label=\"Previous page\"]")).click();

        String currentPagePrevious = driver.findElement(By.className("mat-paginator-range-label")).getText();

        assertEquals("1 – 10 of 1015", currentPagePrevious);
        assertEquals("http://localhost:4200/administrator/manage-subcategories/dashboard", driver.getCurrentUrl());
    }
}
