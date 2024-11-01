package base;

import config.ConfigManager;
import config.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * BaseTest sets up WebDriver before each test and quits it after test completion.
 */
public class BaseTest {

    protected WebDriver driver;
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.initializeDriver();
        driver.get(ConfigManager.getProperty("baseUrl"));
        logger.info("Test started with URL: {}", ConfigManager.getProperty("baseUrl"));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Driver quit after test completion.");
        }
    }
}
