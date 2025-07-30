package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.CommonActions;

/**
 * Represents the left-hand navigation bar including the Qubika logo.
 * Contains all global navigation methods and verifications.
 */
public class SidebarPage extends BasePage {

    @FindBy(xpath = "//img[contains(@src, 'qubika.png')]")
    public WebElement qubikaLogo;

    @FindBy(css = "a[href='#/contributions']")
    public WebElement dashboardLink;

    @FindBy(css = "a[href='#/category-type']")
    public WebElement categoryTypesLink;

    @FindBy(xpath = "//*[text()=' Salir ']//../a")
    public WebElement logoutLink;

    /**
     * Enum representing the menu items in the sidebar. More items can be added as needed.
     */
    public enum MenuItem {
        DASHBOARD,
        CATEGORY_TYPES,
        LOGOUT
    }


    public boolean isLogoDisplayed() {
        CommonActions.waitForPageToLoad();
        return CommonActions.isElementDisplayed(qubikaLogo);
    }

    /**
     * Navigate to the specified menu item in the sidebar.
     * @param menuItem The menu item to navigate to.
     */
    public void navigateTo(MenuItem menuItem) {
        WebElement link = switch (menuItem) {
            case DASHBOARD -> dashboardLink;
            case CATEGORY_TYPES -> categoryTypesLink;
            case LOGOUT -> logoutLink;
        };
        CommonActions.clickElement(link, "Navigate to " + menuItem.name());
    }

    public void logout() {
        CommonActions.clickElement(logoutLink, "Logout from App");
    }
}
