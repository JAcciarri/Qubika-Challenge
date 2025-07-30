package utils;

import config.ConfigLoader;
import org.slf4j.Logger;
import webdriver.DriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public final class CommonActions {

    private static final Duration MAX_TIMEOUT = Duration.ofSeconds(
            Long.parseLong(ConfigLoader.get("timeouts.maxWait")));
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

    /** Wait for the page to fully load by checking document.readyState. */
    public static void waitForPageToLoad() {
        try {
            new WebDriverWait(driver(), MAX_TIMEOUT)
                    .until(webDriver -> ((JavascriptExecutor) webDriver)
                            .executeScript("return document.readyState").equals("complete"));
        } catch (TimeoutException e) {
            throw new RuntimeException("Page did not load within " + MAX_TIMEOUT + " seconds", e);
        }
    }

    /** Select an option from a dropdown by visible text. */
    public static void selectDropdownOption(WebElement dropdown, String optionText) {
        try {
            final By opt = By.xpath(String.format(".//div[@role='option'][.//*[text()=\"%s\"]]", optionText));
            WebElement choice = new WebDriverWait(driver(), MAX_TIMEOUT)
                    .until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(
                            dropdown, opt))
                    .get(0);
            logger.info("Selecting option: {}", optionText);
            if(choice != null) {
                choice.click();
            } else {
                logger.error("Option '{}' not found in dropdown", optionText);
            }
        } catch (NoSuchElementException e) {
            logger.error("Failed to select option '{}': {}", optionText, e.getMessage());
        }
    }

    /** Find elements by locator, returning an empty list if none found. */
    public static List<WebElement> findElements(By locator) {
        try {
            return driver().findElements(locator);
        } catch (NoSuchElementException e) {
            logger.error("No elements found for locator: {}", locator, e);
            return List.of(); // Return an empty list if no elements found
        }
    }

    /** Wait for an element to disappear from the page/DOM. */
    public static void waitForElementToDisappear(WebElement element) {
        try {
            new WebDriverWait(driver(), MAX_TIMEOUT)
                    .until(ExpectedConditions.invisibilityOf(element));
        } catch (TimeoutException e) {
            logger.error("Element did not disappear within {} seconds: {}", MAX_TIMEOUT, element, e);
        }
    }

    /** Wait for an element to become enabled (clickable). */
    public static void waitForElementEnabled(WebElement element) {
        try {
            new WebDriverWait(driver(), MAX_TIMEOUT)
                    .until(ExpectedConditions.elementToBeClickable(element));
        } catch (TimeoutException e) {
            throw new ElementNotInteractableException(
                    "Element not enabled after " + MAX_TIMEOUT + "s: " + element, e);
        }
    }

    /** Wait for an element to contain a specific attribute with a given value. */
    public static void waitForElementContainAttribute(By locator, String attribute, String value) {
        try {
            new WebDriverWait(driver(), MAX_TIMEOUT)
                    .until(ExpectedConditions.attributeContains(locator, attribute, value));
        } catch (TimeoutException e) {
            throw new ElementNotInteractableException(
                    "Element did not contain attribute '" + attribute + "' with value '" + value + "' after " + MAX_TIMEOUT + "s: ", e);
        }
    }
}