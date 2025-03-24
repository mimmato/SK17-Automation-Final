import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class LoginPageObject extends BasePageObject {
    public LoginPageObject(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);

    }
    @FindBy(id = "defaultLoginFormUsername")
    private WebElement usernameOrEmailField;
    @FindBy(id = "defaultLoginFormPassword")
    private WebElement passwordField;
    @FindBy(id = "sign-in-button")
    private WebElement signInButton;
    @FindBy(xpath = "//p[@class='h4 mb-4']")
    private WebElement singInTitle;
    @FindBy(xpath = "//form//a")
    private WebElement registerLink;
    @FindBy(xpath = "//input[@formcontrolname='rememberMe']")
    private WebElement rememberMeCheckbox;
    @FindBy(xpath = "//span[contains(text(), 'Remember me')]")
    private WebElement rememberMeText;
    @FindBy(xpath = "//div[@role='alertdialog']")
    private WebElement toastMessage;

    public WebElement getUsernameOrEmailField() {
        return usernameOrEmailField;
    }

    public String getToastMessage(){
        try {
            WebDriverWait waitToast = new WebDriverWait(webDriver, Duration.ofSeconds(10));
            waitToast.until(ExpectedConditions.visibilityOf(toastMessage));
            return toastMessage.getText();
        } catch (TimeoutException ex) {
            return "Toast message not found";
        }
    }
    public String isRegisterLinkVisible() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(registerLink));
        if (registerLink.isDisplayed()) {
            return registerLink.getText();
        } else {
            return null;
        }
    }
    public void validateLoginElements() {
        String usernameOrEmailPlaceholder = usernameOrEmailField.getAttribute("placeholder");
        Assert.assertEquals(usernameOrEmailPlaceholder, "Username or email", "Username field placeholder text is incorrect: " + usernameOrEmailPlaceholder);

        String passwordPlaceholder = passwordField.getAttribute("placeholder");
        Assert.assertEquals(passwordPlaceholder, "Password", "Password field placeholder text is incorrect: " + passwordPlaceholder);

        String rememberCheckboxText = rememberMeText.getText();
        Assert.assertEquals(rememberCheckboxText, "Remember me", "Remember me checkbox is invalid " + rememberCheckboxText);

        boolean displayCheck = rememberMeCheckbox.isDisplayed();
        Assert.assertTrue(displayCheck, "Remember me");

        String signInButtonText = signInButton.getText();
        Assert.assertEquals(signInButtonText, "Sign in", "Sign in button text is incorrect: " + signInButtonText);

        Assert.assertFalse(isSignInEnabled(), "Sign in button should be disabled when either Username and Password fields are blank.");

        String signInFormTitle = singInTitle.getText();
        Assert.assertEquals(signInFormTitle, "Sign in", "Sign in title is incorrect: " + signInFormTitle);
    }
    public boolean isSignInEnabled(){
        return signInButton.isEnabled();
    }
    public void validateElementsPostLogin(){
//        homeLink.getText();
        Assert.assertEquals(getProfileLinkText(), "Profile", "Profile option in navigation menu is incorrect: " + getProfileLinkText());
        Assert.assertEquals(getHomeLinkText(), "Home", "Home option in navigation menu is incorrect: " + getHomeLinkText());
        Assert.assertEquals(getNewPostLinkText(), "New post", "New Post option in navigation menu is incorrect: " + getNewPostLinkText());
        Assert.assertTrue(getExitIconText(), "Exit icon is not displayed!");
        Assert.assertEquals(getSearchBarText(), "Search","Search bar placeholder is not in place: " + getSearchBarText());

//        String usernameOrEmailPlaceholder = usernameOrEmailField.getAttribute("placeholder");
//        Assert.assertEquals(usernameOrEmailPlaceholder, "Username or email", "Username field placeholder text is incorrect: " + usernameOrEmailPlaceholder);

    }
    public void clearEnterUsernameValidate(String username){
        usernameOrEmailField.clear();
        usernameOrEmailField.sendKeys(username);
        Assert.assertEquals(username, "mishmprotected", "The entered user does NOT match the expected!");
    }
    public void clearEnterEmailValidate(String email){
        usernameOrEmailField.clear();
        usernameOrEmailField.sendKeys(email);
        Assert.assertEquals(email, "mishm2@mish.m", "The entered email does NOT match the expected!");
    }
    public void clearAndEnterPassword(){
        String password = "Testing123!";
        passwordField.clear();
        passwordField.sendKeys(password);
        Assert.assertEquals(password, "Testing123!", "The entered password does NOT match the expected!");

    }
    public void checkRememberMe(){
        rememberMeCheckbox.click();
        Assert.assertTrue(rememberMeCheckbox.isSelected(), "The Remember me box is NOT checked!");
    }
    public void clickSignIn() {
        WebDriverWait waitToast = new WebDriverWait(this.webDriver, Duration.ofSeconds(5));
        waitToast.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@role='alertdialog']")));
        this.signInButton.click();
    }
    public void fillUserInfoCheck(){
        clearEnterUsernameValidate("mishmprotected");
        clearAndEnterPassword();
        checkRememberMe();
        Assert.assertTrue(isSignInEnabled(), "Sign in button should NOT be disabled when either Username and Password fields are blank.");
    }
    public void fillEmailCheck(){
        clearEnterEmailValidate("mishm2@mish.m");
        clearAndEnterPassword();
        checkRememberMe();
        Assert.assertTrue(isSignInEnabled(), "Sign in button should NOT be disabled when either Username and Password fields are blank.");
    }
    public void insertUser(){
        usernameOrEmailField.clear();
        usernameOrEmailField.sendKeys();
    }
    public void clickRegister() {
        this.registerLink.click();
    }

    }




