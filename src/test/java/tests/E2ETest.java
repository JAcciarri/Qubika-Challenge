package tests;

import api.services.UserService;
import api.models.User;
import dataprovider.E2ETestDataProvider;
import factories.UserFactory;
import io.restassured.response.Response;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.CategoriesPage;
import pages.DashboardPage;
import pages.LoginPage;
import pages.SidebarPage;
import utils.CommonActions;
import utils.LoggerUtil;
import webdriver.DriverManager;
import java.util.Map;

public class E2ETest extends BaseTest {

    private static final Logger logger = LoggerUtil.getLogger(E2ETest.class);

    @Test(dataProvider = "e2eTestDataProvider", dataProviderClass = E2ETestDataProvider.class)
    public void testEndToEnd(Map<String, String> testData) {
        WebDriver driver = DriverManager.getDriver();
        LoginPage loginPage = new LoginPage(driver);
        SoftAssert sAssert = new SoftAssert();
        UserService userService = new UserService();
        DashboardPage dashboardPage = new DashboardPage();
        CategoriesPage categoriesPage = new CategoriesPage();

        logTestStart(testData);
        // ──── 1) Create user via API
        logger.info("──── 1) Starting E2E test: Creating user via API");
        User newUser = UserFactory.createRandomUserForRegister();
        Response res = userService.createUser(newUser);
        Assert.assertEquals(res.getStatusCode(), 201, "User creation API did not return 201 Created");
        sAssert.assertTrue(res.getBody().asString().contains(newUser.getEmail()), "API response does not contain created user email");
        sAssert.assertTrue(res.getBody().asString().contains("id"), "API response does not contain user created ID");

        User created = res.as(User.class);
        sAssert.assertNotNull(created.getEmail(), "API did not return created user email");
        sAssert.assertTrue(created.getRoles() != null && created.getRoles().length > 0, "Created user roles are null or empty");
        sAssert.assertEquals(created.getEmail(), newUser.getEmail(), "Created user email does not match requested email");

        // ──── 2-3) Open login page & assert header, and inputs displayed
        logger.info("──── 2-3) Opening login page and verifying header and inputs");
        loginPage.open();
        sAssert.assertEquals(loginPage.getHeaderText(), testData.get("validateHeaderLogin"), "Header text does not match on login page");
        sAssert.assertTrue(CommonActions.isElementDisplayed(loginPage.usernameInput), "Username input is not displayed");
        sAssert.assertTrue(CommonActions.isElementDisplayed(loginPage.passwordInput), "Password input is not displayed");

        // ──── 4-5) Perform login and verify sidebar elements
        logger.info("──── 4-5) Logging in with created user and verifying sidebar elements");
        loginPage.loginAs(created.getEmail(), newUser.getPassword());
        sAssert.assertTrue(dashboardPage.sidebar().isLogoDisplayed(), "Sidebar logo is not displayed after login");
        sAssert.assertTrue(CommonActions.isElementDisplayed(dashboardPage.sidebar().categoryTypesLink), "Category Types link is not displayed in sidebar");

        // ──── 6b) Navigate to Category Types and create a new category
        logger.info("──── 6b) Navigating to Category Types and creating a new category");
        dashboardPage.sidebar().navigateTo(SidebarPage.MenuItem.CATEGORY_TYPES);
        CommonActions.waitForElementDisplayed(categoriesPage.header);
        final String categoryName = testData.get("categoryName") + System.currentTimeMillis();
        categoriesPage.addCategory(categoryName);
        sAssert.assertTrue(CommonActions.isElementDisplayed(categoriesPage.successToastMessage), "Success toast message is not displayed after category creation");
        categoriesPage.navigateToLastPage();
        sAssert.assertTrue(categoriesPage.isCategoryPresent(categoryName), "Category was not created successfully");

        // ──── 6c) Create a sub-category taking parent from the created category
        logger.info("──── 6c) Creating a sub-category under the created category");
        final String subCategoryName = testData.get("subcategoryName") + System.currentTimeMillis();
        categoriesPage.addSubCategory(subCategoryName, categoryName);
        sAssert.assertTrue(CommonActions.isElementDisplayed(categoriesPage.successToastMessage), "Success toast message is not displayed after subcategory creation");
        categoriesPage.navigateToLastPage();
        sAssert.assertTrue(categoriesPage.isCategoryPresent(subCategoryName), "Subcategory was not created successfully");
        sAssert.assertTrue(categoriesPage.isSubcategoryUnderParent(subCategoryName, categoryName), "Subcategory parent is not correct");
        sAssert.assertEquals(categoriesPage.getParentBasedOnCategory(subCategoryName), categoryName, "Subcategory parent does not match the expected category");

        dashboardPage.sidebar().logout();
        sAssert.assertAll();
        logTestEnd(testData);

    }


}
