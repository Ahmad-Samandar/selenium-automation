package tests.auth;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import actions.home.HomePageActions;
import utils.WaitUtils;

/**
 * LoginTest contains test cases for login-related functionalities.
 */
public class LoginTest extends BaseTest {

    @Test
    public void testClickSignInButton() {
        HomePageActions homePageActions = new HomePageActions(driver);

        // Verify that the SignIn button is visible before clicking
        Assert.assertTrue(homePageActions.isSignInButtonVisible(), "Sign In button is not visible on the Home Page");

        WaitUtils.applyGlobalWait();
        // Click the SignIn button
        homePageActions.clickSignIn();

        WaitUtils.applyGlobalWait();

        // Additional validation if needed
        logger.info("Successfully clicked the Sign In button and navigated to the login page.");
    }
}