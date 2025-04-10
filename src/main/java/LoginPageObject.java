import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
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
    public WebElement getUsernameOrEmailField() {
        return usernameOrEmailField;
    }
    public String isRegisterLinkVisible() {
        wait.until(ExpectedConditions.visibilityOf(registerLink));
        if (registerLink.isDisplayed()) {
            return registerLink.getText();
        } else {
            return null;
        }
    }
    public void validateLoginElements() {
        validateUsernameOrEmailPlaceholder();
        validatePasswordPlaceholder();
        validateRememberMeText();
        validateRememberMeCheckboxIsDisplayed();
        validateSignInButtonText();
        validateSignInButtonDisabledByDefault();
        validateSignInTitle();
    }
    private void validateUsernameOrEmailPlaceholder() {
        String placeholder = usernameOrEmailField.getAttribute("placeholder");
        Assert.assertEquals(placeholder, "Username or email", "Username field placeholder text is incorrect: " + placeholder);
    }
    private void validatePasswordPlaceholder() {
        String placeholder = passwordField.getAttribute("placeholder");
        Assert.assertEquals(placeholder, "Password", "Password field placeholder text is incorrect: " + placeholder);
    }
    private void validateRememberMeText() {
        String text = rememberMeText.getText();
        Assert.assertEquals(text, "Remember me", "Remember me checkbox is invalid: " + text);
    }
    private void validateRememberMeCheckboxIsDisplayed() {
        boolean isDisplayed = rememberMeCheckbox.isDisplayed();
        Assert.assertTrue(isDisplayed, "Remember me checkbox should be displayed");
    }
    private void validateSignInButtonText() {
        String text = signInButton.getText();
        Assert.assertEquals(text, "Sign in", "Sign in button text is incorrect: " + text);
    }
    private void validateSignInButtonDisabledByDefault() {
        Assert.assertFalse(isSignInEnabled(), "Sign in button should be disabled when Username and Password fields are blank.");
    }
    private void validateSignInTitle() {
        String title = singInTitle.getText();
        Assert.assertEquals(title, "Sign in", "Sign in title is incorrect: " + title);
    }
    public boolean isSignInEnabled(){
        return signInButton.isEnabled();
    }
    public void validateElementsPostLogin() {
        validateProfileLinkText();
        validateHomeLinkText();
        validateNewPostLinkText();
        validateExitIconDisplayed();
        validateSearchBarPlaceholder();
    }
    private void validateProfileLinkText() {
        String profileText = getProfileLinkText();
        Assert.assertEquals(profileText, "Profile", "Profile option in navigation menu is incorrect: " + profileText);
    }
    private void validateHomeLinkText() {
        String homeText = getHomeLinkText();
        Assert.assertEquals(homeText, "Home", "Home option in navigation menu is incorrect: " + homeText);
    }
    private void validateNewPostLinkText() {
        String newPostText = getNewPostLinkText();
        Assert.assertEquals(newPostText, "New post", "New Post option in navigation menu is incorrect: " + newPostText);
    }
    private void validateExitIconDisplayed() {
        Assert.assertTrue(getExitIconText(), "Exit icon is not displayed!");
    }
    private void validateSearchBarPlaceholder() {
        String searchText = getSearchBarText();
        Assert.assertEquals(searchText, "Search", "Search bar placeholder is not in place: " + searchText);
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
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@role='alertdialog']")));
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
    public void clickRegister() {
        this.registerLink.click();
    }
    }