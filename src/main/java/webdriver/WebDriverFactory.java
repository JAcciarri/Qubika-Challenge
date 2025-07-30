package webdriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import utils.LoggerUtil;

public class WebDriverFactory {

    private static final Logger logger = LoggerUtil.getLogger(WebDriverFactory.class);

    public static WebDriver createDriver(String browser, String executionEnv) {
        boolean headless = "jenkins".equalsIgnoreCase(executionEnv);
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless=new", "--disable-gpu", "--window-size=1920,1080");
                } else {
                    chromeOptions.addArguments("--start-maximized");
                }
                return new ChromeDriver(chromeOptions);

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("-headless", "--width=1920", "--height=1080");
                } else {
                    firefoxOptions.addArguments("--start-maximized");
                }
                return new FirefoxDriver(firefoxOptions);

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) {
                    edgeOptions.addArguments("--headless=new", "--window-size=1920,1080");
                } else {
                    edgeOptions.addArguments("--start-maximized");
                }
                return new EdgeDriver(edgeOptions);

            default:
                logger.error("Supported browsers: chrome, firefox, edge");
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }
}