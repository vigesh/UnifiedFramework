package testCases;

import app.AppSetup;
import org.testng.annotations.*;
import pages.Login;
import utils.Constants;
import utils.ReadProperty;

public class BaseTest  {

    @BeforeSuite
    public void getAllDetails() throws Exception {
        ReadProperty.loadProperties();
    }

    @BeforeTest
    @Parameters({"device","platform","version"})
    public void envSetup(String device, String platform, String version) throws Exception {
        AppSetup.appLaunch(device, platform, version);
        Constants.platform=platform;
    }


    @AfterTest
    public void clearBrowser() throws Exception {
        if(AppSetup.getDriver()!=null){
            AppSetup.getDriver().quit();
        }
    }
    public Login getLogin(){return new Login(); }
}
