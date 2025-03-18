import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePageObject {

    public static final String CURRENT_URL = "http://training.skillo-bg.com:4300";
    public final WebDriver webDriver;

    public BasePageObject(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(this.webDriver, this);
    }

    public boolean isCurrentURLCorrect(String uri) {
        String expectedURL = CURRENT_URL + uri;

        WebDriverWait wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10));
        try {
        wait.until(ExpectedConditions.urlToBe(expectedURL));
        } catch (TimeoutException ex) {
            System.out.println("Expected URL: " + expectedURL);
            System.out.println("Current URL: " + webDriver.getCurrentUrl());
            return false;
        }
        String currentURL = this.webDriver.getCurrentUrl();
        return currentURL.equals(expectedURL);
    }

    public void openURL(String uri) {
        String fullURL = CURRENT_URL + uri;
        webDriver.get(fullURL);
    }
}

