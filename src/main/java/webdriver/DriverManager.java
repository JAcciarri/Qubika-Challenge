package webdriver;

import org.openqa.selenium.WebDriver;

public class DriverManager {
    private static final ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();

    public static void setDriver(WebDriver driver) {
        threadDriver.set(driver);
    }

    public static WebDriver getDriver() {
        WebDriver driver = threadDriver.get();
        if (driver == null) {
            throw new IllegalStateException("WebDriver has not been initialized for this thread.");
        }
        return driver;
    }

    public static void unload() {
        WebDriver driver = threadDriver.get();
        if (driver != null) {
            driver.quit();
            threadDriver.remove();
        }
    }
}