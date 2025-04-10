import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTests extends BaseTestConfig{
    private final String uriHome = "/posts/all";
    @Test
    public void loadPostsWithScroll(){
        WebDriver driver = getDriver();
        BasePageObject basePage = new BasePageObject(driver);
        basePage.openURL(uriHome);
        basePage.verifyCurrentURL(uriHome);
        HomePageObject homePage = new HomePageObject(driver);
        int actualPostCount = homePage.checkPostsLoaded();
        int expectedPostCount = 3;
        Assert.assertEquals(
                actualPostCount,
                expectedPostCount,
                "Expected"
                        + expectedPostCount
                        + "posts, but got a different number: "
                        + actualPostCount);
        int numberOfScrolls = homePage.scrollToLoad(3);
        int expectedPostCountAfterScroll = 3 + (numberOfScrolls * 3);
        actualPostCount = homePage.checkPostsLoaded();
        Assert.assertEquals(
                actualPostCount,
                expectedPostCountAfterScroll,
                "Expected " + expectedPostCountAfterScroll
                        + " posts after "
                        + numberOfScrolls
                        + " scrolls are made, but got a different number: "
                        + actualPostCount);
    }
    @Test
    public void checkPostElements() {
        WebDriver driver = getDriver();
        BasePageObject basePage = new BasePageObject(driver);
        basePage.openURL(uriHome);
        basePage.verifyCurrentURL(uriHome);
        HomePageObject homePage = new HomePageObject(driver);
        homePage.clickPost(2);
        homePage.checkPostInfoElements();
        homePage.clickUserInPostModal();
        driver.navigate().back();
        basePage.verifyCurrentURL(uriHome);
        homePage.clickPost(1);
        homePage.enterCommentInPostModal();
    }
}