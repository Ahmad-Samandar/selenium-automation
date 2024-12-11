package config;

import enums.Browser;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * DriverFactory initializes the WebDriver based on the browser type specified in the configuration,
 * with optional headless mode based on configuration.
 */
public class DriverFactory {
    private static final Logger logger = LogManager.getLogger(DriverFactory.class);
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            initializeDriver();
        }
        return driver.get();
    }

    /**
     * Initializes the WebDriver based on the specified Browser enum and headless mode setting.
     */
    public static void initializeDriver() {
        // Retrieve typed configuration values
        Configuration config = ConfigManager.getConfiguration();
        Browser browser = Browser.valueOf(config.getBrowser().toUpperCase());
        boolean isHeadless = config.isHeadless();

        logger.info("Initializing WebDriver for browser: {}, Headless: {}", browser, isHeadless);

        WebDriver webDriver = createDriver(browser, isHeadless);
        webDriver.manage().window().maximize();

        // Set the WebDriver in ThreadLocal so parallel execution is supported
        driver.set(webDriver);
    }

    /**
     * Creates a WebDriver instance based on the browser and headless configuration.
     *
     * @param browser    The browser to use (CHROME, FIREFOX, EDGE, etc.).
     * @param isHeadless Whether to run in headless mode.
     * @return A configured WebDriver instance.
     */
    private static WebDriver createDriver(Browser browser, boolean isHeadless) {
        switch (browser) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (isHeadless) {
                    chromeOptions.addArguments("--headless", "--disable-gpu", "--window-size=1920,1080");
                }
                return new ChromeDriver(chromeOptions);

            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (isHeadless) {
                    firefoxOptions.addArguments("--headless");
                }
                return new FirefoxDriver(firefoxOptions);

            case EDGE:
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (isHeadless) {
                    edgeOptions.addArguments("--headless");
                }
                return new EdgeDriver(edgeOptions);

            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }
    }

    /**
     * Quits the WebDriver instance.
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            try {
                driver.get().quit();
                logger.info("WebDriver instance quit successfully.");
            } finally {
                driver.remove();
            }
        }
    }
}
