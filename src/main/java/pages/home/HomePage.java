package pages.home;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * HomePage represents the main landing page of the application and contains only locators.
 */
public class HomePage {

    private final WebDriver driver;

    // Locator for the SignIn button
    public final By signInButton = By.id("signinLink");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }
}
