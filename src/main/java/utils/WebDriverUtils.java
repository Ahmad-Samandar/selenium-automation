package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

/**
 * WebDriverUtils provides utility methods for common WebDriver operations.
 */
public class WebDriverUtils {

    /**
     * Scrolls the view to the specified element on the page.
     * @param driver The WebDriver instance.
     * @param element The WebElement to scroll into view.
     */
    public static void scrollToElement(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Takes a screenshot of the current page.
     * @param driver The WebDriver instance.
     * @return The screenshot as a File.
     */
    public static File takeScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    }

    /**
     * Switches the WebDriver context to a specified frame.
     * @param driver The WebDriver instance.
     * @param frameLocator The By locator of the frame to switch to.
     */
    public static void switchToFrame(WebDriver driver, By frameLocator) {
        driver.switchTo().frame(driver.findElement(frameLocator));
    }

    /**
     * Waits for the page to fully load.
     * @param driver The WebDriver instance.
     */
    public static void waitForPageLoad(WebDriver driver) {
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete")
        );
    }
}
