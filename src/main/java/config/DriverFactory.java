package config;

import enums.Browser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * DriverFactory initializes the WebDriver based on the browser type specified in the config,
 * with optional headless mode based on configuration.
 */
public class DriverFactory {
    private static WebDriver driver;

    /**
     * Initializes the WebDriver based on the specified Browser enum and headless mode setting.
     * @return WebDriver instance
     */
    public static WebDriver initializeDriver() {
        Browser browser = Browser.valueOf(ConfigManager.getProperty("browser").toUpperCase());
        boolean isHeadless = Boolean.parseBoolean(ConfigManager.getProperty("headless"));
        System.out.println("Headless mode: " + isHeadless);

        switch (browser) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (isHeadless) {
                    chromeOptions.addArguments("--headless", "--disable-gpu", "--window-size=1920,1080");
                }
                driver = new ChromeDriver(chromeOptions);
                break;

            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (isHeadless) {
                    firefoxOptions.addArguments("--headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;

            case EDGE:
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (isHeadless) {
                    edgeOptions.addArguments("--headless");
                }
                driver = new EdgeDriver(edgeOptions);
                break;

            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }

        driver.manage().window().maximize();
        return driver;
    }

    /**
     * Quits the WebDriver instance.
     */
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
