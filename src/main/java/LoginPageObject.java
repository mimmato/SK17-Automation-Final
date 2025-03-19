import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPageObject extends BasePageObject {
    public LoginPageObject(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this); // Initialize elements

    }

    @FindBy(id = "defaultLoginFormUsername")
    private WebElement usernameField;
    @FindBy(id = "defaultLoginFormPassword")
    private WebElement passwordField;
    @FindBy(id = "sign-in-button")
    private WebElement loginButton;
    @FindBy(xpath = "//p[@class='h4 mb-4']")
    private WebElement singInTitle;
    @FindBy(xpath = "//form//a")
    private WebElement registerLink;

    public String isRegisterLinkVisible() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(registerLink));
        if (registerLink.isDisplayed()) {
            return registerLink.getText();
        } else {
            return null;
        }
    }

        public void clickRegister () {
            this.registerLink.click();
        }
    }

