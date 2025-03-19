import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

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

    public void sendTextToFields(){

    }
}