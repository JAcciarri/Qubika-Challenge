package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import utils.CommonActions;
import webdriver.DriverManager;

public class E2ETest extends BaseTest {

    @Test
    public void testEndToEnd() {
        WebDriver driver = DriverManager.getDriver();
        LoginPage loginPage = new LoginPage(driver);
        SoftAssert sAssert = new SoftAssert();
        // Open the login page and verify is displayed correctly
        loginPage.open();
        sAssert.assertEquals(loginPage.getHeaderText(), "Qubika Club", "Header text does not match on login page");
        sAssert.assertTrue(CommonActions.isElementDisplayed(loginPage.usernameInput), "Username input is not displayed");
        sAssert.assertTrue(CommonActions.isElementDisplayed(loginPage.passwordInput), "Password input is not displayed");

        // Perform login
        loginPage.loginAs("test.qubika@qubika.com", "12345678");


    }


}
