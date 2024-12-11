package stepdefinitions.auth;

import actions.home.HomePageActions;
import base.BaseTest;
import constants.ApplicationConstants;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.testng.Assert;
import utils.WaitUtils;

/**
 * LoginSteps defines Cucumber steps for interacting with the Home Page and verifying redirections.
 */
public class LoginSteps extends BaseTest {

    private HomePageActions homePageActions;

    @Before
    public void setUpDriver() {
        homePageActions = new HomePageActions(driver);
    }

    @Given("the user is on the home page")
    public void theUserIsOnTheHomePage() {}

    @When("the user clicks on the Sign In button")
    public void theUserClicksOnTheSignInButton() {
        // Validate that the SignIn button is visible
        Assert.assertTrue(homePageActions.isSignInButtonVisible(), "Sign In button is not visible on the Home Page");

        // Apply global wait if configured
        WaitUtils.applyGlobalWait();

        // Click the SignIn button
        homePageActions.clickSignIn();

        // Apply global wait after the click, if needed
        WaitUtils.applyGlobalWait();

        logger.info("Successfully clicked the Sign In button.");
    }

    @Then("the user should be redirected to the login page")
    public void theUserShouldBeRedirectedToTheLoginPage() {
        String expectedUrl = ApplicationConstants.LOGIN_URL;
        String actualUrl = driver.getCurrentUrl();

        // Validate that the user is redirected to the login page
        Assert.assertEquals(actualUrl, expectedUrl, "User was not redirected to the login page.");

        logger.info("User was successfully redirected to the login page.");
    }

    @After
    public void tearDownDriver() {
        tearDown();
    }
}
