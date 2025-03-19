import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTests extends BaseTestConfig{

//    private static final Logger log = LoggerFactory.getLogger(HomePageTests.class);

    @Test
    public void verifyLandingPage(){
        WebDriver driver = getDriver();

        BasePageObject basePage = new BasePageObject(driver);

        String uri = "/posts/all";
        basePage.openURL(uri);
        boolean isCorrectURL = basePage.isCurrentURLCorrect(uri);
        Assert.assertTrue(isCorrectURL, "The URL did not match the expected landing page.");

        HomePageObject homePage = new HomePageObject(driver);
        homePage.areThreePostsLoaded();
        Assert.assertTrue(homePage.areThreePostsLoaded(), "Expected 3 posts, but got a different number: " + homePage.postThumbnails.size());







//
//        homePage.isCurrentURLCorrect("/posts/all");
//
//        LoginPageObject loginPage = new LoginPageObject(driver);
//        loginPage.openURL();
//        loginPage.isCurrentURLCorrect("/users/login");



    }
}
