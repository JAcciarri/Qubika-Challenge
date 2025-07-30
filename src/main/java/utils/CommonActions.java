package utils;

import org.slf4j.Logger;
import webdriver.DriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public final class CommonActions {

    private static final Duration MAX_TIMEOUT = Duration.ofSeconds(10);
    private static final Logger logger = LoggerUtil.getLogger(CommonActions.class);
    private CommonActions() {}

    private static WebDriver driver() {
        return DriverManager.getDriver();
    }

    /** Navigate the browser to the given URL. */
    public static void openPage(String url) {
        driver().get(url);
    }

    /** Clear any existing text and type into the element, waiting for visibility. */
    public static void typeText(WebElement element, String text) {
        try {
            logger.info("Typing text: {}", text);
            new WebDriverWait(driver(), MAX_TIMEOUT)
                    .until(ExpectedConditions.visibilityOf(element));
            element.clear();
            element.sendKeys(text);
        } catch (TimeoutException e) {
            throw new ElementNotInteractableException(
                    "Unable to type into element within " + MAX_TIMEOUT + "sec: " + element, e);
        }
    }

    /** Click the element, waiting for it to be clickable. */
    public static void clickElement(WebElement element, String description) {
        try {
            logger.info("Clicking on element: {}", description);
            new WebDriverWait(driver(), MAX_TIMEOUT)
                    .until(ExpectedConditions.elementToBeClickable(element))
                    .click();
        } catch (TimeoutException | StaleElementReferenceException e) {
            throw new ElementNotInteractableException(
                    "Unable to click element within " + MAX_TIMEOUT + "sec: " + element, e);
        }
    }

    /** Get visible text (or 'value' attribute if empty), without waiting. */
    public static String getText(WebElement element) {
        String txt = element.getText().trim();
        if (txt.isEmpty()) {
            String val = element.getAttribute("value");
            return val == null ? "" : val.trim();
        }
        return txt;
    }

    /** Wait until the element is visible on the page. */
    public static void waitForElementDisplayed(WebElement element) {
        try {
            new WebDriverWait(driver(), MAX_TIMEOUT)
                    .until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            throw new NoSuchElementException(
                    "Element not visible after " + MAX_TIMEOUT + "s: " + element, e);
        }
    }

    /** Returns true if element becomes visible within timeout, false otherwise. */
    public static boolean isElementDisplayed(WebElement element) {
        try {
            new WebDriverWait(driver(), MAX_TIMEOUT)
                    .until(ExpectedConditions.visibilityOf(element));
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}