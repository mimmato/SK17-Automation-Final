import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;


public class BaseTestConfig {

    public static final String RESOURCES_DIR = "src/test/resources/";
    public static final String SCREENSHOTS_DIR = RESOURCES_DIR.concat("screenshots/");
    private WebDriver webDriver;

    @BeforeSuite
    @Parameters("browser")
    public void setBeforeSuite(@Optional("chrome") String browser) throws IOException {
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
        }
        makeScreenshotDIR(SCREENSHOTS_DIR);
        delScreenshots();
    }

    public void makeScreenshotDIR(String SCREENSHOTS_DIR) {
        File directory = new File(SCREENSHOTS_DIR);
        if (!directory.exists()) {
            boolean created = directory.mkdirs(); // Creates the directory and any necessary parent directories
            if (created) {
                System.out.println("Screenshots directory created at: " + directory);
            } else {
                System.out.println("Failed to create screenshots directory at: " + directory);
            }
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

    @BeforeMethod
    @Parameters("browser")
    public void setBeforeMethod(@Optional("chrome") String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            this.webDriver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            this.webDriver = new EdgeDriver();
        }
        this.webDriver.manage().window().maximize();
        this.webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        this.webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterMethod
    protected void tearDownTest(ITestResult testResult) {
        takeScreenshotOnFailure(testResult);
        quitDriver();
    }

    protected void delScreenshots() throws IOException {
        System.out.println("Attempting to delete old screenshots...");
        delScreenshotDIR(SCREENSHOTS_DIR);
    }
    private void takeScreenshotOnFailure(ITestResult testResult) {
        if (webDriver == null) {
            System.out.println("WebDriver is null, skipping screenshot.");
            return;
        }

        if (ITestResult.FAILURE == testResult.getStatus()) {
            try {
                File screenshotDir = new File(SCREENSHOTS_DIR);
                if (!screenshotDir.exists()) {
                    screenshotDir.mkdirs();
                }

                TakesScreenshot takeScreenshot = (TakesScreenshot) webDriver;
                File screenshot = takeScreenshot.getScreenshotAs(OutputType.FILE);

                String timestamp = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
                String testName = testResult.getName() + "_" + timestamp;
                testName = testName.replaceAll("[^a-zA-Z0-9_-]", "_");

                File destFile = new File(SCREENSHOTS_DIR.concat(testName).concat(".jpg"));
                FileUtils.copyFile(screenshot, destFile);

                System.out.println("Screenshot saved: " + destFile.getAbsolutePath());

            } catch (IOException ex) {
                System.out.println("Unable to create a screenshot file: " + ex.getMessage());
            }
        }
    }
    protected WebDriver getDriver() {
        return this.webDriver;
    }


    private void quitDriver() {
        if (this.webDriver != null) {
            this.webDriver.quit();
            this.webDriver = null;
            Assert.assertNull(this.webDriver, "WebDriver should be null after quitting.");
        } else {
            System.out.println("WebDriver is null.");
        }
    }

//    @AfterClass
//    public void tearDownClass() {
//        quitDriver();
//    }

    @AfterSuite
    public void tearDownSuite() {
        quitDriver();
    }
}





