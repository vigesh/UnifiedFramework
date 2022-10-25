package testCases;

import Browsers.BrowserSetup;
import com.aventstack.extentreports.ExtentTest;
import extentreports.ExtentTestManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.*;
import pages.Dashboard;
import pages.Login;
import pages.Transfers;
import utils.ReadProperty;

import java.io.File;
import java.io.IOException;

public class BaseTest  {

    public Login getLoginPage() { return new Login(); }
    public Dashboard getDashboardPage() { return new Dashboard(); }
    public Transfers getTransfersPage() { return new Transfers(); }

    @BeforeSuite
    public void getAllDetails() throws Exception {
        ReadProperty.loadProperties();
    }

    @BeforeClass
    public void envSetup(){
        BrowserSetup.openBrowser();
    }

    public byte[] getByteScreenshot() throws IOException
    {
        File src = ((TakesScreenshot) BrowserSetup.getDriver()).getScreenshotAs(OutputType.FILE);
        byte[] fileContent = FileUtils.readFileToByteArray(src);
        return fileContent;
    }
    @AfterClass
    public void clearBrowser() throws Exception {
        if(BrowserSetup.getDriver()!=null){
            BrowserSetup.getDriver().quit();
        }
    }
}
