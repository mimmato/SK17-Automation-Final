import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

        String longUser = registrationPage.genRandomUser(21);
        System.out.println("Testing invalid username: " + longUser + " (Length: " + longUser.length() + ")");
        registrationPage.clearAndEnterUsername(longUser);

        Assert.assertTrue(registrationPage.isUsernameSuccess(), "The " + longUser + " user should NOT be accepted due to feedback message: " +
                registrationPage.getUsernameDangerMess());
    }
    //Title: Username validation message is incorrect. Minimum length is 4 characters, but message says 2.
    @Test
    public void testValidUsernameTooShort() {
        WebDriver driver = getDriver();
        RegistrationPageObject registrationPage = new RegistrationPageObject(driver);

        BasePageObject basePage = new BasePageObject(driver);
        basePage.navigateToRegistrationPage();

        registrationPage.validateFieldsText();

        String shortUser = registrationPage.genRandomUser(2);
        System.out.println("Testing valid username: " + shortUser + " (Length: " + shortUser.length() + ")");
        registrationPage.clearAndEnterUsername(shortUser);
        Assert.assertEquals(registrationPage.isUsernameDanger(), registrationPage.isUsernameSuccess(), "The " + shortUser + " user should be accepted due to feedback message: " +
                registrationPage.getUsernameDangerMess());
    }
    @Test
    public void testValidEmail(){
        WebDriver driver = getDriver();
        RegistrationPageObject registrationPage = new RegistrationPageObject(driver);

        BasePageObject basePage = new BasePageObject(driver);
        basePage.navigateToRegistrationPage();

        String validEmail = registrationPage.genRandomEmail(22);
        System.out.println("Testing valid email: " + validEmail + " (Length: " + validEmail.length() + ")");
        registrationPage.clearAndEnterEmail(validEmail);
    }
    @Test
    public void testValidDOB(){
        WebDriver driver = getDriver();
        RegistrationPageObject registrationPage = new RegistrationPageObject(driver);

        BasePageObject basePage = new BasePageObject(driver);
        basePage.navigateToRegistrationPage();



        registrationPage.clearAndEnterDOB(dob);
    }


//    @Test
//    public void voltron(){
////        testValidEmail();
////        testValidUsername();
//    RegistrationPageObject registrationPageObject = new RegistrationPageObject(getDriver());
//    registrationPageObject.navigateToRegistrationPage();
//    registrationPageObject.clearAndEnterUsername("");
//    registrationPageObject.clearAndEnterEmail("");
//
//}

    //Must contain digit and uppercase letter!
}



