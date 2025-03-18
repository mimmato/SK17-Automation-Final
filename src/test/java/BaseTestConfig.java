import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class BaseTestConfig {

    public static final String RESOURCES_DIR = "src/test/resources/";
    public static final String SCREENSHOTS_DIR = RESOURCES_DIR.concat("screenshots/");

    private WebDriver webDriver;

    @BeforeSuite
    protected void setBeforeSuite() {
        WebDriverManager.chromedriver().setup();

    }

    @BeforeMethod
    protected void setBeforeMethod() {
        this.webDriver = new ChromeDriver(/*configChromeOptions()*/);
        this.webDriver.manage().window().maximize();
        this.webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        this.webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(45));
    }

//    private ChromeOptions configChromeOptions() {
//        Map<String, Object> prefs = new HashMap<>();
//        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.setExperimentalOption("prefs", prefs);
//        return chromeOptions;
//    }

    @AfterMethod
    protected void tearDownTest(ITestResult testResult) {
        takeScreenshotOnFailure(testResult);
        quitDriver();
    }
    @AfterSuite
    protected void delScreenshots() throws IOException{
        delScreenshotDIR(SCREENSHOTS_DIR);
    }

    private void takeScreenshotOnFailure(ITestResult testResult) {
        if (ITestResult.FAILURE == testResult.getStatus()) {
            try {
                TakesScreenshot takeScreenshot = (TakesScreenshot) webDriver;
                File screenshot = takeScreenshot.getScreenshotAs(OutputType.FILE);
                String testName = testResult.getName() + "_" + Thread.currentThread().threadId();

                for (Object param : testResult.getParameters()) {
                    if (!param.toString().isEmpty()) {
                        testName = testName + param;
                    }
                }
                FileUtils.copyFile(screenshot, new File(SCREENSHOTS_DIR.concat(testName).concat(".jpg")));
            } catch (IOException ex) {
                System.out.println("Unable to create a screenshot file: " + ex.getMessage());
            }
        }
    }

    protected WebDriver getDriver(){
        return this.webDriver;
    }

    private void quitDriver() {
        if (this.webDriver != null) {
            this.webDriver.quit();
            this.webDriver = null;
            // Assert that the driver has been quit and the reference is now null
            Assert.assertNull(this.webDriver, "WebDriver should be null after quitting.");
        } else {
            System.out.println("WebDriver is null.");
        }
    }

    private void delScreenshotDIR(String SCREENSHOTS_DIR) throws IOException {
        File directory = new File(SCREENSHOTS_DIR);

        Assert.assertTrue(directory.isDirectory(), "Invalid directory");

        FileUtils.cleanDirectory(directory);

        String[] fileList = directory.list();
        if (fileList != null && fileList.length == 0) {
            System.out.printf("All files are deleted in Directory: %s%n", SCREENSHOTS_DIR);
        } else {
            System.out.printf("Unable to delete the files in Directory: %s%n", SCREENSHOTS_DIR);
        }
    }
}










//    private ChromeOptions configChromeOptions() {
//        // Get the default download directory path
//        String downloadPath = System.getProperty("user.dir") + File.separator + DOWNLOAD_DIR;
//
//        // Set the Chrome preferences
//        Map<String, Object> prefs = new HashMap<>();
//        prefs.put("download.default_directory", downloadPath);
//
//        // Apply preferences to ChromeOptions
//        ChromeOptions options = new ChromeOptions();
//        options.setExperimentalOption("prefs", prefs);
//
//        return options;
//    }

