import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RegistrationPageTests extends BaseTestConfig {

    private static final Logger log = LoggerFactory.getLogger(RegistrationPageTests.class);

    @Test
    public void testValidUsername() {
        WebDriver driver = getDriver();
        RegistrationPageObject registrationPage = new RegistrationPageObject(driver);

        BasePageObject basePage = new BasePageObject(driver);
        basePage.navigateToRegistrationPage();

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
        basePage.navigateToRegistrationPage();

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
        basePage.navigateToRegistrationPage();


        registrationPage.validateFieldsText();

        String validUser = registrationPage.genRandomUser(2);
        System.out.println("Testing valid username: " + validUser + " (Length: " + validUser.length() + ")");
        registrationPage.enterAndValidateUsername(validUser);
    }
}



