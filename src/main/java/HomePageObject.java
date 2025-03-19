import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
    @FindBy(tagName = "body")
    protected WebElement bodyScroll;

    public int checkPostsLoaded() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfAllElements(postThumbnails));

        System.out.println("Number of posts found: " + postThumbnails.size());
        return postThumbnails.size();
    }

    public void scrollToLoad(){
        for (int i = 0 ; i < 15; i++){
            bodyScroll.sendKeys(Keys.PAGE_DOWN);
            WebDriverWait scrollWait = new WebDriverWait(this.webDriver, Duration.ofSeconds(5));
//            scrollWait.until(ExpectedConditions.numberOfElementsToBe("post-feed-container", 6));
            scrollWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className("post-feed-container"), postThumbnails.size()));
        }
    }
}

