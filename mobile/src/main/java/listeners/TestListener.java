package listeners;

import app.AppSetup;
import com.aventstack.extentreports.Status;
import extentreports.ExtentTestManager;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Attachment;
import jira.JiraCreateIssue;
import jira.JiraServiceProvider;
import logs.Log;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.Constants;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static extentreports.ExtentManager.getExtentReports;
import static extentreports.ExtentTestManager.getTest;

public class TestListener implements ITestListener {
    public AndroidDriver<MobileElement> driver= AppSetup.getDriver();
    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    //Text attachments for Allure
    @Attachment(value="Page screenshot", type="image/png")
    public byte[] saveScreenshotPNG(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    public String saveScreenshot(WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String dest = System.getProperty("user.dir") + "\\extent-reports\\Screenshots\\" + Constants.testName + "_" + System.currentTimeMillis() + ".png";
        File destination = new File(dest);
        FileUtils.copyFile(source, destination);
        return dest;
    }

    //Text attachments for Allure
    @Attachment(value = "{0}", type = "text/plain")
    public static String saveTextLog(String message) {
        return message;
    }
    //HTML attachments for Allure
    @Attachment(value = "{0}", type = "text/html")
    public static String attachHtml(String html) {
        return html;
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        Log.info("I am in onStart method " + iTestContext.getName());
        iTestContext.setAttribute("WebDriver", this.driver);
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        Log.info("I am in onFinish method " + iTestContext.getName());
        //Do tier down operations for ExtentReports reporting!
        getExtentReports().flush();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        Log.info(getTestMethodName(iTestResult) + " test is starting.");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        Log.info(getTestMethodName(iTestResult) + " test is succeed.");
        //ExtentReports log operation for passed tests.
        getTest().log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        Log.info(getTestMethodName(iTestResult) + " test is failed.");

        //Get driver from BaseTest and assign to local webdriver variable.
        Object testClass = iTestResult.getInstance();
        driver = AppSetup.getDriver();

        //Allure ScreenShotRobot and SaveTestLog
        if (driver != null) {
            Log.error("Screenshot captured for test case:" + getTestMethodName(iTestResult));
            saveScreenshotPNG(driver);
        }

        //Save a log on allure.
        saveTextLog(getTestMethodName(iTestResult) + " failed and screenshot taken!");

        //Take base64Screenshot screenshot for extent reports
        String base64Screenshot =
                "data:image/png;base64," + ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);

        //ExtentReports log and screenshot operations for failed tests.
        getTest().log(Status.FAIL, "Test Failed: " + iTestResult.getThrowable(),
                getTest().addScreenCaptureFromBase64String(base64Screenshot).getModel().getMedia().get(0));

    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        Log.info(getTestMethodName(iTestResult) + " test is skipped.");
        //ExtentReports log operation for skipped tests.
        getTest().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        Log.info("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
    }

    public void takePassScrenshot(String description) throws IOException {
        String screenshot = saveScreenshot(driver);
        getTest().log(Status.PASS, description, ExtentTestManager.getTest().addScreenCaptureFromPath(screenshot).getModel().getMedia().get(0));

    }

    public void createIssueInJiraOnFailure(ITestResult result) throws IOException {
        boolean islogIssue = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(JiraCreateIssue.class).isCreateIssue();

        if (islogIssue) {
            JiraServiceProvider JiraServiceProvider = new JiraServiceProvider("https://digital-hub-delivery.atlassian.net/", "62a07983e318a50069383cd5", "3eTYaEDjL6Y3DYzdziHU2942", "DH");
            String issueDescription = "Failure Reason from Automation Testing\n\n" + result.getThrowable().getMessage();
            issueDescription.concat(ExceptionUtils.getMessage(result.getThrowable()));
            String issueSummary = result.getMethod().getConstructorOrMethod().getMethod().getName()+ "Failed in Automation Testing";
            //JiraServiceProvider.createJiraIssue("Bug", issueSummary, issueDescription, "62a07983e318a50069383cd5");
            JiraServiceProvider.createJiraIssueForFailure("Bug", issueSummary, issueDescription);
        }
    }
}
