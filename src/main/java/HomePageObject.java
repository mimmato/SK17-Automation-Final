import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

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
    @FindBy(xpath = "//div[@class='container post-info-container']//div//strong[contains(text(), 'likes')]")
    private WebElement numOfLikesText;
    @FindBy(xpath = "//div[@class='col-12 post-info']//div[@class='post-date']")
    private WebElement postDateText;
    @FindBy(xpath = "//input")
    private WebElement commentPlaceholder;
    @FindBy(xpath = "//div[@class='post-modal-container']//div[@class='post-title']")
    private WebElement postTitle;
    @FindBy(xpath = "//div[@class='post-modal-container']//a[@class='post-user']")
    private WebElement postUserLink;



    public void clickPost(int postPosition) {
        // this method is limited to 3 indexes to avoid the need to scroll
        if (postOpen.isEmpty()) {
            throw new NoSuchElementException("No posts found");
        }
        if (postPosition < 0 || postPosition >= postOpen.size()) {
            throw new IndexOutOfBoundsException("Post index is not within the expected post position: " + postPosition +
                    ". Must be between 0 and " + (postOpen.size() - 1));
        }

        postOpen.get(postPosition).click();
    }
    public void checkPostInfoElements(){
        Assert.assertTrue(LikeIcon.isDisplayed(), "The Like Icon is not displayed!");
        Assert.assertTrue(numOfLikesText.getText().contains("likes"), "The number of likes text is not correct!");
        Assert.assertTrue(postDateText.getText().contains("CREATED"), "The Post date text is not correct!");
        Assert.assertEquals(commentPlaceholder.getAttribute("placeholder"), "Comment here", "The Comment placeholder is missing" );
        Assert.assertTrue(postTitle.isDisplayed(), "Post title is not displayed");
    }

    public void clickUserInPostModal(){
        WebDriver driver = webDriver;

        postUserLink.click();

        LoginPageObject loginPage = new LoginPageObject(driver);
        loginPage.getToastMessage();

        Assert.assertEquals(loginPage.getToastMessage(), "You must be logged in in order to see this page!");

        BasePageObject basePage = new BasePageObject(driver);
        String uri = "/users/login";
        boolean isCorrectURL = basePage.isCurrentURLCorrect(uri);
        Assert.assertTrue(isCorrectURL, "The URL did not match the expected login page: " + uri);
    }

    public void enterCommentInPostModal() throws InterruptedException {
        WebDriver driver = webDriver;
        LoginPageObject loginPage = new LoginPageObject(driver);

        WebDriverWait waitToast = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        waitToast.until(ExpectedConditions.invisibilityOf(loginPage.getToastMessageElement()));

        commentPlaceholder.sendKeys("Test");
        commentPlaceholder.sendKeys(Keys.ENTER);

        Assert.assertEquals(loginPage.getToastMessage(), "You must login");
    }


    public int checkPostsLoaded() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfAllElements(postThumbnails));
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
                initialCount = updatedPosts.size();
            } else {
                break;
            }
            scrolls++;
        }
    }
}


