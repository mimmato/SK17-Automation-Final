import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

public class RegistrationPageObject extends BasePageObject {
    public RegistrationPageObject(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(xpath = "//input[@formcontrolname='username']")
    private WebElement regUsernameField;
    @FindBy(xpath = "//input[@formcontrolname='email']")
    private WebElement regEmailField;
    @FindBy(xpath = "//input[@formcontrolname='birthDate']")
    private WebElement regBirthDateField;
    @FindBy(xpath = "//input[@formcontrolname='password']")
    private WebElement regPasswordField;
    @FindBy(xpath = "//input[@formcontrolname='confirmPassword']")
    private WebElement regConfirmPasswordField;
    @FindBy(xpath = "//textarea[@formcontrolname='publicInfo']")
    //typo in public in textarea class
    private WebElement regPublicInfoField;
    @FindBy(xpath = "//button[@id='sign-in-button']")
    private WebElement regSignInButton;

    @FindBy(xpath = "//div[@class='input-filed has-danger']")
    private WebElement textFieldsParentDivDanger;
    @FindBy(xpath = "//div[@class='input-filed has-success']")
    private List<WebElement> textFieldsParentDivSuccess;

    @FindBy(xpath = "//div[@aria-label='Username taken']")
    private WebElement usernameTakenToast;

    @FindBy(xpath = "//div[@role='alertdialog']")
    private WebElement toastMessage;

    public String getToastMessage(){
        try {
            WebDriverWait waitToast = new WebDriverWait(webDriver, Duration.ofSeconds(10));
            waitToast.until(ExpectedConditions.visibilityOf(toastMessage));
            return toastMessage.getText();
        } catch (TimeoutException ex) {
            return "Toast message not found";
        }
    }
    public String getTextFieldsDangerMess(){
        return textFieldsParentDivDanger.getText();
    }
//    public String getTextFieldsSuccessMess(){
//        String messagesResult = "";
//        for (WebElement element : textFieldsParentDivSuccess){
//            messagesResult = element.getText();
//        } return messagesResult;
//
//    }
    public int getNumSuccessMess(){
        return textFieldsParentDivSuccess.size();
    }
    public void validateFieldsText() {
        String usernamePlaceholder = regUsernameField.getAttribute("placeholder");
        Assert.assertEquals(usernamePlaceholder, "Username", "Username field placeholder text is incorrect: " + usernamePlaceholder);

        String emailPlaceholder = regEmailField.getAttribute("placeholder");
        Assert.assertEquals(emailPlaceholder, "email", "Email field placeholder text is incorrect: " + emailPlaceholder);

        String birthDatePlaceholder = regBirthDateField.getAttribute("placeholder");
        Assert.assertEquals(birthDatePlaceholder, "Birth date", "Birth Date field placeholder text is incorrect: " + birthDatePlaceholder);

        String passwordPlaceholder = regPasswordField.getAttribute("placeholder");
        Assert.assertEquals(passwordPlaceholder, "Password", "Password field placeholder text is incorrect: " + passwordPlaceholder);

        String confirmPasswordPlaceholder = regConfirmPasswordField.getAttribute("placeholder");
        Assert.assertEquals(confirmPasswordPlaceholder, "Confirm Password", "Confirm Password field placeholder text is incorrect: " + confirmPasswordPlaceholder);

        String publicInfoPlaceholder = regPublicInfoField.getAttribute("placeholder");
        Assert.assertEquals(publicInfoPlaceholder, "Public info", "Public info field placeholder text is incorrect: " + publicInfoPlaceholder);

        String signInButtonText = regSignInButton.getText();
        Assert.assertEquals(signInButtonText, "Sign in", "Sign in button text is incorrect: " + signInButtonText);
    }
    public String genRandomUser(int desiredLength) {
        String randomUsername = UUID.randomUUID().toString().replace("-", "").substring(0, desiredLength);
        return randomUsername.substring(0, Math.min(desiredLength, randomUsername.length()));
    }
    public void clearAndEnterUsername(String username){
        regUsernameField.clear();
        regUsernameField.sendKeys(username);
    }
    public void enterAndValidateUsername(String username) {
        clearAndEnterUsername(username);

        String enteredUsername = regUsernameField.getAttribute("value");
        Assert.assertEquals(enteredUsername, username, "Entered username does not match the expected value.");

        Assert.assertTrue(isUsernameSuccess() || isUsernameDanger(), "Username field status is unknown.");

        if (isUsernameSuccess()) {
            Assert.assertTrue(isUsernameSuccess(), "Username is Valid.");
        } else if (isUsernameDanger()) {
            Assert.assertFalse( isUsernameDanger(), "Username is Invalid.");
        }

    }
    public boolean isUsernameSuccess() {
        try {
            WebDriverWait waitUserSuccessMess = new WebDriverWait(webDriver, Duration.ofSeconds(1));
            waitUserSuccessMess.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='input-filed has-success']")));
            return !textFieldsParentDivSuccess.isEmpty();

        } catch (NoSuchElementException | TimeoutException ex) {
            return false;
        }
    }
    public boolean isUsernameDanger() {
        try {
            WebDriverWait waitUserDangerMess = new WebDriverWait(webDriver, Duration.ofSeconds(1));
            waitUserDangerMess.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='input-filed has-danger']")));
//            return usernameParentDivDanger.getText();
            return textFieldsParentDivDanger.getAttribute("class").contains("has-danger");
        } catch (NoSuchElementException | TimeoutException ex) {
            return false;
        }
    }
    public void testValidUsername() {

        String validUser = genRandomUser(20);
        enterAndValidateUsername(validUser);
    }
    public void testInvalidEmailWith21Char(){

        String validEmail = genRandomEmail(21);
        clearAndEnterEmail(validEmail);
    }
    public void testValidEmailWith20Characters(){

        String validEmail = genRandomEmail(20);
        clearAndEnterEmail(validEmail);
    }
    public void testValidDOB()  {
        clearAndEnterDOB("23-07-1993");
    }
    public void testValidPassword() {
        clearAndEnterPass("Testing123!");
    }
    public void testValidPublicInfo() {
        clearAndEnterPublicInfo("Testing");
    }
    public void clickSignInButton(){
        regSignInButton.click();
    }
    public void cleanRegUsernameField(){
        regUsernameField.clear();
    }
    public String genRandomEmail(int desiredLength){
        String domain = "@t.m";
        int maxEmailLength = desiredLength - domain.length();

        String randomEmail = UUID.randomUUID().toString().replace("-", "").substring(0, maxEmailLength);
        return randomEmail + domain;
    }
    public void clearAndEnterEmail(String email){
        regEmailField.clear();
        regEmailField.sendKeys(email);
    }
    public void clearAndEnterDOB(String date){
        regBirthDateField.clear();
        regBirthDateField.sendKeys(date);
    }
    public void clearAndEnterPass(String password){
        regPasswordField.clear();
        regPasswordField.sendKeys(password);
        regConfirmPasswordField.clear();
        regConfirmPasswordField.sendKeys(password);
    }
    public void clearAndEnterPublicInfo(String text){
        regPublicInfoField.clear();
        regPublicInfoField.sendKeys(text);
    }

}
