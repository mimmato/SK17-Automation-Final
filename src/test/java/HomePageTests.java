import org.openqa.selenium.WebDriver;
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
        Assert.assertTrue(isCorrectURL, "The URL did not match the expected landing page: " + uri);

        HomePageObject homePage = new HomePageObject(driver);

        int actualPostCount = homePage.checkPostsLoaded();
        Assert.assertEquals(actualPostCount, 3,
                "Expected 3 posts, but got a different number: " + actualPostCount);

        homePage.scrollToLoad(3);
        int actualPostCountAfterScroll = homePage.checkPostsLoaded();
        Assert.assertEquals(actualPostCountAfterScroll, actualPostCount * 4,
                "Expected 12 posts after 3 scrolls to END, but got a different number: " + actualPostCountAfterScroll);
    }
}
