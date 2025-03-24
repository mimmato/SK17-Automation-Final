import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RegistrationPageTests extends BaseTestConfig {

    @Test
    public void testInvalidUsernameTooLong() {
        WebDriver driver = getDriver();
        RegistrationPageObject registrationPage = new RegistrationPageObject(driver);
        BasePageObject basePage = new BasePageObject(driver);
        basePage.navigateToRegistrationPage();
        registrationPage.validateFieldsText();
        String longUser = registrationPage.genRandomUser(21);
        registrationPage.clearAndEnterUsername(longUser);
        Assert.assertTrue(registrationPage.isUsernameDanger(), "Username is less than 20 characters and should be accepted!");
    }
    @Test
    public void testValidUsernameTooShortBug() {
        WebDriver driver = getDriver();
        RegistrationPageObject registrationPage = new RegistrationPageObject(driver);

        BasePageObject basePage = new BasePageObject(driver);
        basePage.navigateToRegistrationPage();

        registrationPage.validateFieldsText();

        String shortUser = registrationPage.genRandomUser(2);
        registrationPage.clearAndEnterUsername(shortUser);
        Assert.assertEquals(registrationPage.isUsernameDanger(), registrationPage.isUsernameSuccess(), "Bug detected: " +
                "The '" + shortUser + "' user should be accepted due to feedback message: " +
                registrationPage.getTextFieldsDangerMess());
    }
    @Test
    public void testRegWithSuccessMessagesEmailBug() {
        WebDriver driver = getDriver();
        BasePageObject basePage = new BasePageObject(driver);
        basePage.navigateToRegistrationPage();
        RegistrationPageObject registrationPage = new RegistrationPageObject(driver);
        registrationPage.validateFieldsText();
        registrationPage.testValidUsername();
        registrationPage.testInvalidEmailWith21Char();
        registrationPage.testValidDOB();
        registrationPage.testValidPassword();
        registrationPage.testValidPublicInfo();
        registrationPage.clickSignInButton();
        registrationPage.getToastMessage();
        registrationPage.getNumSuccessMess();
        if (registrationPage.getToastMessage().equals("Registration failed!") && registrationPage.getNumSuccessMess() == 6) {
            Assert.fail("Bug detected: Registration failed with success messages count of 6!");
        }
    }
    @Test
    public void testRegSuccess() {
            WebDriver driver = getDriver();
            BasePageObject basePage = new BasePageObject(driver);
            basePage.navigateToRegistrationPage();
            RegistrationPageObject registrationPage = new RegistrationPageObject(driver);
            registrationPage.validateFieldsText();
            registrationPage.testValidUsername();
            registrationPage.testValidEmailWith20Characters();
            registrationPage.testValidDOB();
            registrationPage.testValidPassword();
            registrationPage.testValidPublicInfo();
            registrationPage.clickSignInButton();
            registrationPage.getToastMessage();
            registrationPage.getNumSuccessMess();
            if (registrationPage.getToastMessage().equals("Registration failed!") && registrationPage.getNumSuccessMess() == 6) {
                Assert.fail("Bug detected: Registration failed with success messages count of 6!");
            }
            registrationPage.isCurrentURLCorrect("/posts/all");
    }
}



