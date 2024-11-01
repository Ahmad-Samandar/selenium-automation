package utils;

import config.ConfigManager;
import enums.WaitStrategy;
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

    /**
     * Retrieves the timeout duration from config properties, with a default value of 10 seconds if not set.
     * @return Duration based on timeout property.
     */
    private static Duration getTimeout() {
        String timeoutValue = ConfigManager.getProperty("timeout");
        int timeoutInSeconds = (timeoutValue != null) ? Integer.parseInt(timeoutValue) : 10;
        return Duration.ofSeconds(timeoutInSeconds);
    }

    /**
     * Applies a fixed delay if global wait is enabled in the config.
     */
    public static void applyGlobalWait() {
        boolean enableWait = Boolean.parseBoolean(ConfigManager.getProperty("enableWait"));
        int waitInSeconds = Integer.parseInt(ConfigManager.getProperty("waitInSeconds", "0"));
        System.out.println("Is wait enabled: " + enableWait);
        System.out.println("Wait in seconds: " + waitInSeconds);
        if (enableWait && waitInSeconds > 0) {
            try {
                Thread.sleep(waitInSeconds * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Global wait interrupted: " + e.getMessage());
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
        WebDriverWait wait = new WebDriverWait(driver, getTimeout());

        switch (strategy) {
            case CLICKABLE:
                return wait.until(ExpectedConditions.elementToBeClickable(element));
            case VISIBLE:
                return wait.until(ExpectedConditions.visibilityOf(element));
            case NONE:
            default:
                return element;
        }
    }

    /**
     * Waits for a locator based on the PRESENCE strategy.
     * @param driver The WebDriver instance.
     * @param locator The By locator to wait for.
     * @return The WebElement after it is present in the DOM.
     */
    public static WebElement waitForPresence(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, getTimeout());
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
}
