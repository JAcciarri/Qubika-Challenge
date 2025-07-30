package pages;

import config.ConfigLoader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonActions;

public class LoginPage {

    public LoginPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    private final String URL = ConfigLoader.get("ui.loginUrl");

    // Locators
    private static final String USERNAME_INPUT = "[formcontrolname='email']";
    private static final String PASSWORD_INPUT = "[formcontrolname='password']";
    private static final String LOGIN_BUTTON = "button[type='submit']";
    private static final String HEADER = "//h3[text()='Qubika Club']";

    // Web Elements
    @FindBy(css = USERNAME_INPUT)
    public WebElement usernameInput;
    @FindBy(css = PASSWORD_INPUT)
    public WebElement passwordInput;
    @FindBy(css = LOGIN_BUTTON)
    public WebElement loginButton;
    @FindBy(xpath = HEADER)
    public WebElement loginHeader;


    public LoginPage open() {
        CommonActions.openPage(URL);
        // Wait for the page to load
        CommonActions.waitForElementDisplayed(loginHeader);
        return this;
    }


    public String getHeaderText() {
        return CommonActions.getText(loginHeader);
    }

    public void loginAs(String username, String password) {
        CommonActions.typeText(usernameInput, username);
        CommonActions.typeText(passwordInput, password);
        CommonActions.clickElement(loginButton, "Login Button");
    }

}
