package testCases;

import app.AppSetup;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import utils.ReadProperty;

public class BaseTest  {

    @BeforeSuite
    public void getAllDetails() throws Exception {
        ReadProperty.loadProperties();
    }

    @BeforeClass
    @Parameters({"device","platform","version"})
    public void envSetup(String device, String platform, String version) throws Exception {
        AppSetup.appLaunch(device, platform, version);
    }


    @AfterClass
    public void clearBrowser() throws Exception {
        if(AppSetup.getDriver()!=null){
            AppSetup.getDriver().quit();
        }
    }
    //public Login getLogin(){return new Login(AppSetup.getDriver()); }
}
