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
//        homePage.checkPostsLoaded();

        int actualPostCount = homePage.checkPostsLoaded();
        Assert.assertEquals(actualPostCount, 3,
                "Expected 3 posts, but got a different number: " + actualPostCount);


        System.out.println("First call of scroll method!");
        homePage.scrollToLoad();
        homePage.checkPostsLoaded();
//        System.out.println("2nd call of scroll method!");
//        homePage.scrollToLoad();
//        homePage.checkPostsLoaded();
//        System.out.println("3rd call of scroll method!");
//        homePage.scrollToLoad();
//        homePage.checkPostsLoaded();




//
//        homePage.isCurrentURLCorrect("/posts/all");
//
//        LoginPageObject loginPage = new LoginPageObject(driver);
//        loginPage.openURL();
//        loginPage.isCurrentURLCorrect("/users/login");



    }
}
