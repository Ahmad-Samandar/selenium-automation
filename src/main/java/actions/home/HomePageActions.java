package actions.home;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.home.HomePage;
import enums.WaitStrategy;
import utils.WaitUtils;

/**
 * HomePageActions provides actions for interacting with elements on the HomePage.
 */
public class HomePageActions {

    private final WebDriver driver;
    private final HomePage homePage;

    public HomePageActions(WebDriver driver) {
        this.driver = driver;
        this.homePage = new HomePage(driver);
    }

    /**
     * Clicks the SignIn button.
     */
    public void clickSignIn() {
        WebElement element = WaitUtils.applyWait(driver, driver.findElement(homePage.signInButton), WaitStrategy.CLICKABLE);
        element.click();
    }

    /**
     * Checks if the SignIn button is visible.
     * @return true if Sign In button is visible, false otherwise.
     */
    public boolean isSignInButtonVisible() {
        WebElement element = WaitUtils.applyWait(driver, driver.findElement(homePage.signInButton), WaitStrategy.VISIBLE);
        return element.isDisplayed();
    }
}
