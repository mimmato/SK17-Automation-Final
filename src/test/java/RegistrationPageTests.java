import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RegistrationPageTests extends BaseTestConfig {

    private static final Logger log = LoggerFactory.getLogger(RegistrationPageTests.class);

//    @Test
//    public void navToRegistrationPage() {
//        WebDriver driver = getDriver();
//
//        BasePageObject basePage = new BasePageObject(driver);
//
//        String uri = "/posts/all";
//        basePage.openURL(uri);
//        boolean isCorrectURL = basePage.isCurrentURLCorrect(uri);
//        Assert.assertTrue(isCorrectURL, "The URL did not match the expected Landing page.");
//        basePage.clickLoginLink();
//
//        LoginPageObject loginPage = new LoginPageObject(driver);
//
//        uri = "/users/login";
//        isCorrectURL = loginPage.isCurrentURLCorrect(uri);
//        Assert.assertTrue(isCorrectURL, "The URL did not match the expected Login page: " + uri);
//
//        loginPage.isRegisterLinkVisible();
//
//        String registerText = loginPage.isRegisterLinkVisible();
//        Assert.assertEquals(registerText, "Register", "The Register link text does not match the expected: " + registerText);
//        loginPage.clickRegister();
//
//        RegistrationPageObject registrationPage = new RegistrationPageObject(driver);
//
//        uri = "/users/register";
//        isCorrectURL = registrationPage.isCurrentURLCorrect(uri);
//        Assert.assertTrue(isCorrectURL, "The URL did not match the expected Registration page.");
//
//        registrationPage.validateFieldsText();
//
//        //user length should be between 2 and 20 characters as per the page invalid-feedback
//        //<span _ngcontent-lgj-c4="" class="invalid-feedback ng-star-inserted"> Maximum 20 characters! </span>
//        //<span _ngcontent-lgj-c4="" class="invalid-feedback ng-star-inserted"> Minimum 2 characters ! </span>
//    }


    @Test
    public void testValidUsername() {
        WebDriver driver = getDriver();
        RegistrationPageObject registrationPage = new RegistrationPageObject(driver);

        BasePageObject basePage = new BasePageObject(driver);
        basePage.navigateToRegistrationPage(); // This takes care of the entire navigation

        // Now proceed with the username validation
        registrationPage.validateFieldsText();

        String validUser = registrationPage.genRandomUser(20);
        System.out.println("Testing valid username: " + validUser + " (Length: " + validUser.length() + ")");
        registrationPage.enterAndValidateUsername(validUser);
    }

    @Test
    public void testInvalidUsernameTooLong() {
        WebDriver driver = getDriver();
        RegistrationPageObject registrationPage = new RegistrationPageObject(driver);

        BasePageObject basePage = new BasePageObject(driver);
        basePage.navigateToRegistrationPage(); // This takes care of the entire navigation

        // Now proceed with the username validation
        registrationPage.validateFieldsText();

        String validUser = registrationPage.genRandomUser(27);
        System.out.println("Testing valid username: " + validUser + " (Length: " + validUser.length() + ")");
        registrationPage.enterAndValidateUsername(validUser);
    }

    //Title: Username validation message is incorrect. Minimum length is 4 characters, but message says 2.
    @Test
    public void testInvalidUsernameTooShort() {
        WebDriver driver = getDriver();
        RegistrationPageObject registrationPage = new RegistrationPageObject(driver);

        BasePageObject basePage = new BasePageObject(driver);
        basePage.navigateToRegistrationPage(); // This takes care of the entire navigation

        // Now proceed with the username validation
        registrationPage.validateFieldsText();

        String validUser = registrationPage.genRandomUser(2);
        System.out.println("Testing valid username: " + validUser + " (Length: " + validUser.length() + ")");
        registrationPage.enterAndValidateUsername(validUser);
    }
}



