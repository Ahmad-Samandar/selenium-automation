package base;

import config.ConfigManager;
import config.Configuration;
import config.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * BaseTest sets up the WebDriver before each test and quits it after test completion.
 */
public class BaseTest {

    protected WebDriver driver;
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeMethod
    public void setUp() {
        // Retrieve typed configuration
        Configuration config = ConfigManager.getConfiguration();

        // Get the driver from the DriverFactory
        driver = DriverFactory.getDriver();
        String baseUrl = config.getBaseUrl();
        driver.get(baseUrl);

        logger.info("Test started with URL: {}", baseUrl);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            DriverFactory.quitDriver();
            logger.info("Driver quit after test completion.");
        }
    }
}
