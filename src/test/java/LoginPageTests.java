import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPageTests extends BaseTestConfig{

    private static final Logger log = LoggerFactory.getLogger(LoginPageTests.class);

    @Test
    public void testLoginUserSuccessExit() {
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
    public void testLoginEmailSuccessExitRemember() throws InterruptedException {
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
}
