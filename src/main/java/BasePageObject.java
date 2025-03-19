import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class BasePageObject {

    @FindBy(id = "nav-link-profile")
    private WebElement profileLink;
    @FindBy(id = "nav-link-login")
    private WebElement loginLink;
    @FindBy(id = "nav-link-home")
    private WebElement homeLink;
    @FindBy(id = "nav-link-new-post")
    private WebElement newPostLink;


    public static final String CURRENT_URL = "http://training.skillo-bg.com:4300";
    public final WebDriver webDriver;

    public BasePageObject(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(this.webDriver, this);
    }

    public boolean isCurrentURLCorrect(String uri) {
        String expectedURL = CURRENT_URL + uri;

        WebDriverWait wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.urlToBe(expectedURL));
        } catch (TimeoutException ex) {
            System.out.println("Expected URL: " + expectedURL);
            System.out.println("Current URL: " + webDriver.getCurrentUrl());
            return false;
        }
        String currentURL = this.webDriver.getCurrentUrl();
        return currentURL.equals(expectedURL);
    }

    public void openURL(String uri) {
        String fullURL = CURRENT_URL + uri;
        webDriver.get(fullURL);
    }

    public void clickLoginLink() {
        WebDriverWait loginPageLinkWait = new WebDriverWait(this.webDriver, Duration.ofSeconds(3));
        loginPageLinkWait.until(ExpectedConditions.elementToBeClickable(this.loginLink));
        this.loginLink.click();
    }

    public void navigateToRegistrationPage() {
        String uri = "/posts/all"; // starting point, could be home or another page
        openURL(uri);
        // Assuming BasePageObject has the openURL method that does the navigation
        Assert.assertTrue(isCurrentURLCorrect(uri), "The URL did not match the expected Landing page.");

        clickLoginLink(); // Navigate to login page

        LoginPageObject loginPage = new LoginPageObject(webDriver);
        uri = "/users/login";
        Assert.assertTrue(loginPage.isCurrentURLCorrect(uri), "The URL did not match the expected Login page.");

        loginPage.clickRegister(); // Click on the register link

        RegistrationPageObject registrationPage = new RegistrationPageObject(webDriver);
        uri = "/users/register";
        Assert.assertTrue(registrationPage.isCurrentURLCorrect(uri), "The URL did not match the expected Registration page.");
    }
}

