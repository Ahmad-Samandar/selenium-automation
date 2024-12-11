package utils;

import config.ConfigManager;
import config.Configuration;
import enums.WaitStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * WaitUtils provides utility methods to wait for elements based on different strategies.
 */
public class WaitUtils {
    private static final Logger logger = LogManager.getLogger(WaitUtils.class);

    // Cache configuration values to avoid repeated lookups
    private static final Configuration config = ConfigManager.getConfiguration();
    private static final Duration TIMEOUT = Duration.ofSeconds(config.getTimeout());
    private static final boolean ENABLE_WAIT = config.isEnableWait();
    private static final int WAIT_IN_SECONDS = config.getWaitInSeconds();

    /**
     * Applies a fixed delay if global wait is enabled in the config.
     */
    public static void applyGlobalWait() {
        logger.debug("Global Wait Enabled: {}", ENABLE_WAIT);
        logger.debug("Global Wait Duration: {} seconds", WAIT_IN_SECONDS);

        if (ENABLE_WAIT && WAIT_IN_SECONDS > 0) {
            try {
                Thread.sleep(WAIT_IN_SECONDS * 1000L);
                logger.debug("Applied global wait of {} seconds.", WAIT_IN_SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.warn("Global wait interrupted: {}", e.getMessage());
            }
        }
    }

    /**
     * Waits for an element based on the specified wait strategy.
     * @param driver The WebDriver instance.
     * @param element The WebElement to apply the wait to.
     * @param strategy The wait strategy to apply.
     * @return The WebElement after applying the wait.
     */
    public static WebElement applyWait(WebDriver driver, WebElement element, WaitStrategy strategy) {
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);

        switch (strategy) {
            case CLICKABLE:
                logger.debug("Waiting for element to be clickable.");
                return wait.until(ExpectedConditions.elementToBeClickable(element));
            case VISIBLE:
                logger.debug("Waiting for element to be visible.");
                return wait.until(ExpectedConditions.visibilityOf(element));
            case NONE:
            default:
                logger.debug("No explicit wait applied.");
                return element;
        }
    }

//    /**
//     * Waits for a locator based on the PRESENCE strategy.
//     * @param driver The WebDriver instance.
//     * @param locator The By locator to wait for.
//     * @return The WebElement after it is present in the DOM.
//     */
//    public static WebElement waitForPresence(WebDriver driver, By locator) {
//        logger.debug("Waiting for presence of element located by: {}", locator);
//        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);
//        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
//    }
}
