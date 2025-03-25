import org.openqa.selenium.WebDriver;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPageTests extends BaseTestConfig {

    @Test
    public void loginWithUserAndRememberMe() {
        WebDriver driver = getDriver();
        BasePageObject basePage = new BasePageObject(driver);
        basePage.navigateToLoginPage();

        LoginPageObject loginPage = new LoginPageObject(driver);
        loginPage.validateLoginElements();
        loginPage.fillUserInfoCheck();
        loginPage.clickSignIn();
        Assert.assertEquals(loginPage.getToastMessage(), "Successful login!", "Toast message is wrong, you are NOT logged in!");
        Assert.assertTrue(loginPage.isCurrentURLCorrect("/posts/all"), "The URL is incorrect! ");
        loginPage.validateElementsPostLogin();

        basePage.clickExit();
        Assert.assertEquals(loginPage.getToastMessage(), "Successful logout!", "Toast message is wrong, you are NOT logged out!");
        Assert.assertTrue(loginPage.isCurrentURLCorrect("/users/login"), "The URL is incorrect! ");
        loginPage.clickSignIn();
        Assert.assertEquals(loginPage.getToastMessage(), "Successful login!", "Toast message is wrong, you are NOT logged in!");
    }
    @Test
    public void loginWithEmailAndRememberMe() throws InterruptedException {
        WebDriver driver = getDriver();
        BasePageObject basePage = new BasePageObject(driver);
        basePage.navigateToLoginPage();

        LoginPageObject loginPage = new LoginPageObject(driver);
        loginPage.validateLoginElements();
        loginPage.fillEmailCheck();
        loginPage.clickSignIn();
        Assert.assertEquals(loginPage.getToastMessage(), "Successful login!", "Toast message is wrong, you are NOT logged in!");
        Assert.assertTrue(loginPage.isCurrentURLCorrect("/posts/all"), "The URL is incorrect! ");
        loginPage.validateElementsPostLogin();

        basePage.clickExit();
        Assert.assertEquals(loginPage.getToastMessage(), "Successful logout!", "Toast message is wrong, you are NOT logged out!");
        Assert.assertTrue(loginPage.isCurrentURLCorrect("/users/login"), "The URL is incorrect! ");
        loginPage.clickSignIn();
        Assert.assertEquals(loginPage.getToastMessage(), "Successful login!", "Toast message is wrong, you are NOT logged in!");
    }
    @Test
    public void loginWrongUser() throws InterruptedException {
        WebDriver driver = getDriver();
        BasePageObject basePage = new BasePageObject(driver);
        basePage.navigateToLoginPage();
        insertWrongUserAndPass();
    }
    public void insertWrongUserAndPass(){
        WebDriver driver = getDriver();
        LoginPageObject loginPage = new LoginPageObject(driver);

        RegistrationPageObject registrationPage = new RegistrationPageObject(driver);
        String wrongUser = registrationPage.genRandomUser(10);

        loginPage.getUsernameOrEmailField().clear();
        loginPage.getUsernameOrEmailField().sendKeys(wrongUser);

        loginPage.clearAndEnterPassword();
        loginPage.clickSignIn();
        Assert.assertEquals(loginPage.getToastMessage(), "Wrong username or password!", "Toast message is wrong, you are NOT logged in!");
    }

}
