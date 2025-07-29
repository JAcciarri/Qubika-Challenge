package tests;

import org.openqa.selenium.WebDriver;
import webdriver.DriverManager;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webdriver.WebDriverFactory;

public abstract class BaseTest {
    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);

    @BeforeMethod(alwaysRun = true)
    public void setUp(ITestContext context) {
        String browser      = context.getCurrentXmlTest().getParameter("browser");
        String executionEnv = context.getCurrentXmlTest().getParameter("executionEnv");
        if (browser == null || browser.isEmpty()) {
            browser = "chrome"; // Default browser
        }
        if (executionEnv == null || executionEnv.isEmpty()) {
            executionEnv = "local"; // Default execution environment
        }
        log.info("Initializing WebDriver: browser={}, env={}", browser, executionEnv);
        WebDriver driver = WebDriverFactory.createDriver(browser);
        DriverManager.setDriver(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        log.info("Quitting WebDriver for thread {}", Thread.currentThread().getId());
        DriverManager.unload();
    }
}