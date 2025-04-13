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
    @FindBy(xpath = "//i[contains(@class, 'fa-sign-out-alt')]")
    private WebElement logoutIcon;
    @FindBy(id = "search-bar")
    private WebElement searchBar;
    @FindBy(xpath = "//div[@role='alertdialog']")
    private WebElement toastMessage;

    protected final String CURRENT_URL = "http://training.skillo-bg.com:4300";
    protected final WebDriver webDriver;
    protected final WebDriverWait wait;
    private String expectedURL;
    private String actualURL;

    public static final String URI_HOME = "/posts/all";
    public static final String URI_LOGIN = "/users/login";
    public static final String URI_REGISTER = "/users/register";


    public BasePageObject(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        PageFactory.initElements(this.webDriver, this);
    }
    public boolean isCurrentURLCorrect(String uri) {
        expectedURL = CURRENT_URL + uri;
        try {
            wait.until(ExpectedConditions.urlToBe(expectedURL));
        } catch (TimeoutException ex) {
            return false;
        }
        String currentURL = this.webDriver.getCurrentUrl();
        return currentURL.equals(expectedURL);
    }
    public void verifyCurrentURL(String expectedURI) {
        expectedURL = CURRENT_URL + expectedURI;
        actualURL = webDriver.getCurrentUrl();
        Assert.assertEquals(
                actualURL,
                expectedURL,
                "The URI did not match the expected for the landing page: " + expectedURI
        );
    }
    public void openURL(String uri) {
        String fullURL = CURRENT_URL + uri;
        webDriver.get(fullURL);
    }
    public void clickLoginLink() {
        wait.until(ExpectedConditions.elementToBeClickable(this.loginLink));
        this.loginLink.click();
    }
    public void navigateToRegistrationPage() {
//        String uri = "/posts/all";
        openURL(URI_HOME);
        Assert.assertTrue(
                isCurrentURLCorrect(URI_HOME),
                "The URL did not match the expected Landing page.");
        clickLoginLink();
        LoginPageObject loginPage = new LoginPageObject(webDriver);
//        uri = "/users/login";
        Assert.assertTrue(
                loginPage.isCurrentURLCorrect(URI_LOGIN),
                "The URL did not match the expected Login page.");
        loginPage.clickRegister();
        RegistrationPageObject registrationPage = new RegistrationPageObject(webDriver);
//        uri = "/users/register";
        Assert.assertTrue(
                registrationPage.isCurrentURLCorrect(URI_REGISTER),
                "The URL did not match the expected Registration page.");
    }
    public void navigateToLoginPage(){
//        String uri = "/posts/all";
        openURL(URI_HOME);
        clickLoginLink();
        LoginPageObject loginPage = new LoginPageObject(webDriver);
//        uri = "/users/login";
        Assert.assertTrue(
                loginPage.isCurrentURLCorrect(URI_LOGIN),
                "The URL did not match the expected Login page.");
    }
    public String getProfileLinkText() {
        return profileLink.getText();
    }
    public String getHomeLinkText() {
        return homeLink.getText();
    }
    public String getNewPostLinkText() {
        return newPostLink.getText();
    }
    public boolean isExitIconVisible() {
        return logoutIcon.isDisplayed();
    }
    public String getSearchBarText() {
        return searchBar.getAttribute("placeholder");
    }
    public void clickExit(){
        this.logoutIcon.click();
    }
    public WebElement getToastMessageElement(){
        try {
            wait.until(ExpectedConditions.visibilityOf(toastMessage));
            return toastMessage;
        } catch (TimeoutException ex) {
            return null;
        }
    }
    public String getToastMessage(){
        try {
            wait.until(ExpectedConditions.visibilityOf(toastMessage));
            return toastMessage.getText();
        } catch (TimeoutException ex) {
            return "Toast message not found";
        }
    }
}