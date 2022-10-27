package testCases;

import Browsers.BrowserSetup;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
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

    @BeforeMethod
    @Parameters({"browser","os","version"})
    public void envSetup(String browser, String OS, String version){
        BrowserSetup.openBrowser(browser, OS, version);
    }

    public byte[] getByteScreenshot() throws IOException
    {
        File src = ((TakesScreenshot) BrowserSetup.getDriver()).getScreenshotAs(OutputType.FILE);
        byte[] fileContent = FileUtils.readFileToByteArray(src);
        return fileContent;
    }
    @AfterMethod
    public void clearBrowser() throws Exception {
        if(BrowserSetup.getDriver()!=null){
            BrowserSetup.getDriver().quit();
        }
    }
}
