import org.openqa.selenium.*;
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

    @FindBy(xpath = "//div[@class='post-feed-img']//img")
    private List<WebElement> postOpen;

    @FindBy(css = "i.like")
    private WebElement LikeIcon;



    public void clickPost(int postPosition) {
        if (postOpen.isEmpty()) {
            throw new NoSuchElementException("No posts found");
        }

        if (postPosition < 0 || postPosition >= postOpen.size()) {
            throw new IndexOutOfBoundsException("Post index is not within the expected post position: " + postPosition +
                    ". Must be between 0 and " + (postOpen.size() - 1));
        }

        postOpen.get(postPosition).click();
    }

    public void checkPostElements(){

    }



    public int checkPostsLoaded() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfAllElements(postThumbnails));
//        System.out.println("Number of posts found: " + postThumbnails.size());
        return postThumbnails.size();
    }

    public void scrollToLoad(int maxScrolls) {
        int initialCount = postThumbnails.size();
        int scrolls = 0;

        while (scrolls < maxScrolls) {
            bodyScroll.sendKeys(Keys.END);

            WebDriverWait scrollWait = new WebDriverWait(webDriver, Duration.ofSeconds(3));
            scrollWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                    By.className("post-feed-container"), initialCount
            ));

            List<WebElement> updatedPosts = webDriver.findElements(By.className("post-feed-container"));
            if (updatedPosts.size() > initialCount) {
//                System.out.println("Posts loaded: " + updatedPosts.size());
                initialCount = updatedPosts.size();
            } else {
//                System.out.println("No more posts loaded.");
                break;
            }
            scrolls++;
        }
    }
}


