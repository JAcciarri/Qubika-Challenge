package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import config.ConfigLoader;
import io.restassured.RestAssured;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import utils.ExtentManager;
import webdriver.DriverManager;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webdriver.WebDriverFactory;

import java.lang.reflect.Method;
import java.util.Map;

public abstract class BaseTest {
    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);
    protected static ExtentReports extent;
    protected static ExtentTest test;

    protected static void step(String msg) {
        log.info(msg);
        test.info(msg);
    }

    @BeforeSuite
    public void configureRestAssured() {
        RestAssured.baseURI = ConfigLoader.get("api.baseUrl");
        extent = ExtentManager.getExtentReports();
    }
    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method, ITestContext context) {
        //  Create a new test entry in Extent
        String testName = method.getName();
        String description = method.isAnnotationPresent(Test.class)
                ? method.getAnnotation(Test.class).description()
                : "";
        test = extent.createTest(testName, description);
        log.info("=== TEST START [{}] {} ===", testName, description);
        test.info("Starting test: " + testName);

        // Initialize WebDriver
        String browser      = context.getCurrentXmlTest().getParameter("browser");
        String executionEnv = context.getCurrentXmlTest().getParameter("executionEnv");
        if (browser == null || browser.isEmpty()) {
            browser = "chrome"; // Default browser
        }
        if (executionEnv == null || executionEnv.isEmpty()) {
            executionEnv = "local"; // Default execution environment
        }

        WebDriver driver = WebDriverFactory.createDriver(browser, executionEnv);
        DriverManager.setDriver(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        switch (result.getStatus()) {
            case ITestResult.SUCCESS:
                test.pass("Test passed");
                break;
            case ITestResult.FAILURE:
                test.fail(result.getThrowable());
                break;
            case ITestResult.SKIP:
                test.skip("Test skipped: " + result.getThrowable());
                break;
            default:
                test.info("Test status: " + result.getStatus());
        }
        log.info("Quitting WebDriver for thread {}", Thread.currentThread().getId());
        DriverManager.unload();
    }

    @AfterSuite
    public void afterSuite() {
        extent.flush();
    }


    public static void logTestStart(Map<String, String> testData) {
        step("##### Starting Test: "+ testData.get("testCaseId")+ " #####");
        step("##### " + testData.get("testDescription") + " #####");
    }
    public static void logTestEnd(Map<String, String> testData) {
        step("##### Ending Test: " + testData.get("testCaseId") + " #####");
    }
}