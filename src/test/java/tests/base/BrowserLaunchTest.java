package tests.base;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * BrowserLaunchTest verifies that the browser opens and navigates to the correct URL.
 */
public class BrowserLaunchTest extends BaseTest {

    @Test
    public void testBrowserLaunch() {
        String expectedUrl = "https://www.facebook.com/";
        String actualUrl = driver.getCurrentUrl();

        // Log a message to confirm that the test is running
        logger.info("Verifying the browser launched and navigated to the correct URL.");

        // Assert that the browser opened to the expected URL
        Assert.assertEquals(actualUrl, expectedUrl, "The browser did not navigate to the expected URL.");
    }
}
