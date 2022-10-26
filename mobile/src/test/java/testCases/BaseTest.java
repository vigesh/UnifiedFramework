package testCases;

import app.AppSetup;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import pages.Login;
import utils.ReadProperty;

import java.net.MalformedURLException;

public class BaseTest  {

    @BeforeSuite
    public void getAllDetails() throws Exception {
        ReadProperty.loadProperties();
    }

    @BeforeClass
    public void envSetup() throws MalformedURLException {
        AppSetup.appLaunch();
    }


    @AfterClass
    public void clearBrowser() throws Exception {
        if(AppSetup.getDriver()!=null){
            AppSetup.getDriver().quit();
        }
    }
    public Login getLogin(){return new Login(); }
}
