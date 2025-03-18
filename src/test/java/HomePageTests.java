import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class HomePageTests extends BaseTestConfig{

    @Test
    public void verifyLandingPage(){
        WebDriver driver = getDriver();
        HomePageObject homePage = new HomePageObject(driver);

        homePage.openURL();
        homePage.isCurrentURLCorrect("/posts/all");



    }


}
