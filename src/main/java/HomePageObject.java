import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePageObject {

    public static final String CURRENT_URL = "http://training.skillo-bg.com:4300";
    public final WebDriver webDriver;

    public HomePageObject(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(this.webDriver, this);
    }

    public void isCurrentURLCorrect(String uri){
        String expectedURL = CURRENT_URL + uri;

        WebDriverWait wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10)); // 10 seconds wait
        wait.until(ExpectedConditions.urlToBe(expectedURL));

        String currentURL = this.webDriver.getCurrentUrl();

        System.out.println("Expected URL: " + expectedURL);
        System.out.println("Current URL: " + currentURL);

    }

    protected void openURL(){
        webDriver.get(CURRENT_URL);
    }


}
