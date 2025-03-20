import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPageTests extends BaseTestConfig{

    private static final Logger log = LoggerFactory.getLogger(LoginPageTests.class);

    @Test
    public void testLoginSuccess() {
        WebDriver driver = getDriver();
        BasePageObject basePage = new BasePageObject(driver);
        basePage.navigateToLoginPage();

        LoginPageObject loginPage = new LoginPageObject(driver);
        loginPage.validateLoginElements();

        loginPage.fillCheckClick();

//        loginPage.getToastMessage();
        Assert.assertEquals(loginPage.getToastMessage(), "Successful login!", "Toast message is wrong, you are NOT logged in!");

        loginPage.isCurrentURLCorrect("/posts/all");
        System.out.println(loginPage.isCurrentURLCorrect("/posts/all"));



    }
}
