import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RegistrationPageTests extends BaseTestConfig {

//    private static final Logger log = LoggerFactory.getLogger(RegistrationPageTests.class);

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
                registrationPage.getTextFieldsDangerMess());
    }

    //Title: Username validation message is incorrect. Minimum length is 4 characters, but message says 2.
    @Test
    public void testValidUsernameTooShortBug() {
        WebDriver driver = getDriver();
        RegistrationPageObject registrationPage = new RegistrationPageObject(driver);

        BasePageObject basePage = new BasePageObject(driver);
        basePage.navigateToRegistrationPage();

        registrationPage.validateFieldsText();

        String shortUser = registrationPage.genRandomUser(2);
        System.out.println("Testing valid username: " + shortUser + " (Length: " + shortUser.length() + ")");
        registrationPage.clearAndEnterUsername(shortUser);
        Assert.assertEquals(registrationPage.isUsernameDanger(), registrationPage.isUsernameSuccess(), "The '" + shortUser + "' user should be accepted due to feedback message: " +
                registrationPage.getTextFieldsDangerMess());
    }

//    @Test
//    public void testValidEmail() {
//        WebDriver driver = getDriver();
//        RegistrationPageObject registrationPage = new RegistrationPageObject(driver);
//
//        BasePageObject basePage = new BasePageObject(driver);
//        basePage.navigateToRegistrationPage();
//
//        String validEmail = registrationPage.genRandomEmail(22);
//        System.out.println("Testing valid email: " + validEmail + " (Length: " + validEmail.length() + ")");
//        registrationPage.clearAndEnterEmail(validEmail);
//    }
//    @Test
//    public void testValidDOB() throws InterruptedException {
//        WebDriver driver = getDriver();
//        RegistrationPageObject registrationPage = new RegistrationPageObject(driver);
//        BasePageObject basePage = new BasePageObject(driver);
//        basePage.navigateToRegistrationPage();
//        registrationPage.clearAndEnterDOB("23-07-1993");
//    }

//    @Test
//    public void testValidPassword() {
//        WebDriver driver = getDriver();
//        RegistrationPageObject registrationPage = new RegistrationPageObject(driver);
//        BasePageObject basePage = new BasePageObject(driver);
//        basePage.navigateToRegistrationPage();
//        registrationPage.clearAndEnterPass("Testing123!");
//    }
//    @Test
//    public void testValidPublicInfo() {
//        WebDriver driver = getDriver();
//        RegistrationPageObject registrationPage = new RegistrationPageObject(driver);
//        BasePageObject basePage = new BasePageObject(driver);
//        basePage.navigateToRegistrationPage();
//        registrationPage.clearAndEnterPublicInfo("Testing");
//    }
    @Test
    public void testRegWithSuccessMessagesBug() {
        WebDriver driver = getDriver();
        BasePageObject basePage = new BasePageObject(driver);
        basePage.navigateToRegistrationPage();
        RegistrationPageObject registrationPage = new RegistrationPageObject(driver);
        registrationPage.validateFieldsText();

        registrationPage.testValidUsername();
        registrationPage.testValidEmailWith21Char();
        registrationPage.testValidDOB();
        registrationPage.testValidPassword();
        registrationPage.testValidPublicInfo();
        registrationPage.clickSignInButton();

        registrationPage.getToastMessage();
        System.out.println(registrationPage.getToastMessage());

        registrationPage.getNumSuccessMess();
        System.out.println(registrationPage.getNumSuccessMess());

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
            System.out.println(registrationPage.getToastMessage());

            registrationPage.getNumSuccessMess();
            System.out.println(registrationPage.getNumSuccessMess());

            if (registrationPage.getToastMessage().equals("Registration failed!") && registrationPage.getNumSuccessMess() == 6) {
                Assert.fail("Bug detected: Registration failed with success messages count of 6!");
            }

            registrationPage.isCurrentURLCorrect("/posts/all");
            System.out.println(registrationPage.isCurrentURLCorrect("/posts/all"));

        //Must contain digit and uppercase letter!
    }
}



