package tests;

import api.services.UserService;
import api.models.User;
import factories.UserFactory;
import io.restassured.response.Response;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.CategoriesPage;
import pages.DashboardPage;
import pages.LoginPage;
import pages.SidebarPage;
import utils.CommonActions;
import webdriver.DriverManager;

public class E2ETest extends BaseTest {

    @Test(description = "Create a user via API, log in via UI, then create & verify categories")
    public void testEndToEnd() {
        WebDriver driver = DriverManager.getDriver();
        LoginPage loginPage = new LoginPage(driver);
        SoftAssert sAssert = new SoftAssert();
        UserService userService = new UserService();
        DashboardPage dashboardPage = new DashboardPage();
        CategoriesPage categoriesPage = new CategoriesPage();

        // ──── 1) Create user via API
        User newUser = UserFactory.createRandomUserForRegister();
        Response res = userService.createUser(newUser);
        Assert.assertEquals(res.getStatusCode(), 201, "User creation API did not return 201 Created");
        sAssert.assertTrue(res.getBody().asString().contains(newUser.getEmail()), "API response does not contain created user email");
        sAssert.assertTrue(res.getBody().asString().contains("id"), "API response does not contain user created ID");

        User created = res.as(User.class);
        sAssert.assertNotNull(created.getEmail(), "API did not return created user email");
        sAssert.assertTrue(created.getRoles() != null && created.getRoles().length > 0, "Created user roles are null or empty");
        sAssert.assertEquals(created.getEmail(), newUser.getEmail(), "Created user email does not match requested email");

        // ──── 2) Open login page & assert header, and inputs displayed
        loginPage.open();
        sAssert.assertEquals(loginPage.getHeaderText(), "Qubika Club", "Header text does not match on login page");
        sAssert.assertTrue(CommonActions.isElementDisplayed(loginPage.usernameInput), "Username input is not displayed");
        sAssert.assertTrue(CommonActions.isElementDisplayed(loginPage.passwordInput), "Password input is not displayed");

        // ──── 3) Perform login and verify sidebar elements
        loginPage.loginAs(created.getEmail(), newUser.getPassword());
        sAssert.assertTrue(dashboardPage.sidebar().isLogoDisplayed(), "Sidebar logo is not displayed after login");
        sAssert.assertTrue(CommonActions.isElementDisplayed(dashboardPage.sidebar().categoryTypesLink), "Category Types link is not displayed in sidebar");

        // ──── 4) Navigate to Category Types and create a new category
        dashboardPage.sidebar().navigateTo(SidebarPage.MenuItem.CATEGORY_TYPES);
        CommonActions.waitForElementDisplayed(categoriesPage.header);
        categoriesPage.clickAddCategory();


        sAssert.assertAll();

    }


}
