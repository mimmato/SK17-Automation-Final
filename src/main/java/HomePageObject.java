import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePageObject extends BasePageObject{
    public HomePageObject(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this); // Initialize elements
    }

    @FindBy(className = "post-feed-container")
    protected List<WebElement> postThumbnails;

    public boolean areThreePostsLoaded() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfAllElements(postThumbnails));

        System.out.println("Number of posts found: " + postThumbnails.size());
        return postThumbnails.size() == 3;
    }
}

