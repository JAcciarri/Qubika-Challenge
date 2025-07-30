package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import webdriver.DriverManager;

public class BasePage {

    private SidebarPage sidebar;
    protected final WebDriver driver;
    /**
     * Initializes the WebDriver and injects @FindBy elements via PageFactory.
     * Also instantiates the SidebarPage for global navigation.
     */
    protected BasePage() {
        this.driver = DriverManager.getDriver();
        PageFactory.initElements(driver, this);
    }

    public SidebarPage sidebar() {
        if (sidebar == null) {
            sidebar = new SidebarPage();
        }
        return sidebar;
    }
}
