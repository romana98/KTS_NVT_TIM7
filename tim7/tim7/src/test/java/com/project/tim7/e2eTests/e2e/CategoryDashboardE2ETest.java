package com.project.tim7.e2eTests.e2e;

import com.project.tim7.e2eTests.pages.CategoryDashboardPage;
import com.project.tim7.e2eTests.pages.MainPagePage;
import com.project.tim7.e2eTests.pages.SignInPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryDashboardE2ETest {

    private WebDriver driver;

    private SignInPage signInPage;
    private MainPagePage mainPagePage;
    private CategoryDashboardPage categoryDashboardPage;

    @Before
    public void setUp() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        categoryDashboardPage = PageFactory.initElements(driver, CategoryDashboardPage.class);
        signInPage = PageFactory.initElements(driver, SignInPage.class);
        mainPagePage = PageFactory.initElements(driver, MainPagePage.class);

        //sign in
        driver.get("http://localhost:4200/sign-in");
        // logging in as administrator
        signInPage.getUsername().sendKeys("mico");
        signInPage.getPassword().sendKeys("123qweASD");
        signInPage.getSignInBtn().click();
        justWait(4000);
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
    public void testAddCategorySuccess() throws InterruptedException {

        mainPagePage.getManageCategoriesNav().click();

        justWait(1000);

        categoryDashboardPage.ensureIsDisplayedAddCategoryName();

        categoryDashboardPage.getAddCategoryNameInput().sendKeys("New category");
        categoryDashboardPage.getAddCategoryButton().click();

        justWait(1000);

        String snackBarText = categoryDashboardPage.getSnackBar().getText();

        assertEquals("Category added successfully.\nOk", snackBarText);
        assertEquals("http://localhost:4200/administrator/manage-categories/dashboard", driver.getCurrentUrl());

        for(int i = 0;i < 100; i++){
            driver.findElement(By.xpath("//*[@aria-label=\"Next page\"]")).click();
        }

        categoryDashboardPage.getDeleteButton().click();

        justWait(1000);

        snackBarText = categoryDashboardPage.getSnackBar().getText();

        assertEquals("Category deleted successfully.\nOk", snackBarText);
        assertEquals("http://localhost:4200/administrator/manage-categories/dashboard", driver.getCurrentUrl());

    }

    @Test
    public void testAddCategoryFail() throws InterruptedException {

        mainPagePage.getManageCategoriesNav().click();

        justWait(1000);

        categoryDashboardPage.ensureIsDisplayedAddCategoryName();

        categoryDashboardPage.getAddCategoryNameInput().sendKeys("Category1");
        categoryDashboardPage.getAddCategoryButton().click();

        justWait(1000);

        String snackBarText = categoryDashboardPage.getSnackBar().getText();

        assertEquals("Category with that name already exists!\nOk", snackBarText);
        assertEquals("http://localhost:4200/administrator/manage-categories/dashboard", driver.getCurrentUrl());

    }

    @Test
    public void testUpdateCategorySuccess() throws InterruptedException {
        mainPagePage.getManageCategoriesNav().click();

        justWait(1000);

        categoryDashboardPage.ensureIsDisplayedAddCategoryName();

        categoryDashboardPage.getClickedRow().click();

        justWait(1000);

        categoryDashboardPage.ensureIsDisplayedUpdateCategoryName();
        categoryDashboardPage.getEditCategoryNameInput().clear();
        categoryDashboardPage.getEditCategoryNameInput().sendKeys("New category edit");
        categoryDashboardPage.getEditCategoryButton().click();

        justWait(1000);

        String snackBarText = categoryDashboardPage.getSnackBar().getText();

        assertEquals("Category updated successfully.\nOk", snackBarText);
        assertEquals("http://localhost:4200/administrator/manage-categories/dashboard", driver.getCurrentUrl());

        for(int i = 0;i < 100; i++){
            driver.findElement(By.xpath("//*[@aria-label=\"Next page\"]")).click();
        }

        categoryDashboardPage.getClickedRow().click();

        justWait(1000);

        categoryDashboardPage.ensureIsDisplayedUpdateCategoryName();
        categoryDashboardPage.getEditCategoryNameInput().clear();
        categoryDashboardPage.getEditCategoryNameInput().sendKeys("Institucija");
        categoryDashboardPage.getEditCategoryButton().click();

        justWait(1000);

        snackBarText = categoryDashboardPage.getSnackBar().getText();

        assertEquals("Category updated successfully.\nOk", snackBarText);
        assertEquals("http://localhost:4200/administrator/manage-categories/dashboard", driver.getCurrentUrl());


    }

    @Test
    public void testUpdateCategoryFail() throws InterruptedException {
        mainPagePage.getManageCategoriesNav().click();

        justWait(1000);

        categoryDashboardPage.ensureIsDisplayedAddCategoryName();

        categoryDashboardPage.getClickedRowFail().click();

        justWait(1000);

        categoryDashboardPage.ensureIsDisplayedUpdateCategoryName();
        categoryDashboardPage.getEditCategoryNameInput().clear();
        categoryDashboardPage.getEditCategoryNameInput().sendKeys("Category5");
        categoryDashboardPage.getEditCategoryButton().click();

        justWait(1000);

        String snackBarText = categoryDashboardPage.getSnackBar().getText();

        assertEquals("Category with that name already exists!\nOk", snackBarText);
        assertEquals("http://localhost:4200/administrator/manage-categories/dashboard", driver.getCurrentUrl());
    }

    @Test
    public void testDeleteFail() throws InterruptedException {

        mainPagePage.getManageCategoriesNav().click();

        justWait(1000);

        categoryDashboardPage.ensureIsDisplayedAddCategoryName();

        categoryDashboardPage.getDeleteButtonFail().click();

        justWait(1000);

        String snackBarText = categoryDashboardPage.getSnackBar().getText();

        assertEquals("Deleting failed! Check if selected category has subcategories!\nOk", snackBarText);
        assertEquals("http://localhost:4200/administrator/manage-categories/dashboard", driver.getCurrentUrl());

    }

    @Test
    public void paginationNextBackTestSuccess() throws InterruptedException {

        mainPagePage.getManageCategoriesNav().click();

        justWait(1000);

        driver.findElement(By.xpath("//*[@aria-label=\"Next page\"]")).click();

        String currentPageNext = driver.findElement(By.className("mat-paginator-range-label")).getText();

        assertEquals("11 – 20 of 1003", currentPageNext);



        driver.findElement(By.xpath("//*[@aria-label=\"Previous page\"]")).click();

        String currentPagePrevious = driver.findElement(By.className("mat-paginator-range-label")).getText();

        assertEquals("1 – 10 of 1003", currentPagePrevious);
        assertEquals("http://localhost:4200/administrator/manage-categories/dashboard", driver.getCurrentUrl());
    }
}
