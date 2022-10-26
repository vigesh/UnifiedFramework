package app;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.Constants;

import java.net.MalformedURLException;
import java.net.URL;

public class AppSetup {

    public static AppiumDriver driver;
    public static String gridURL = "@mobile-hub.lambdatest.com/wd/hub";

    public static AppiumDriver getDriver() {
        return driver;
    }

    public static void appLaunch() throws MalformedURLException {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("build","Java TestNG Android");
        capabilities.setCapability("name","EBANQ");
        capabilities.setCapability("deviceName", Constants.appDetails.get("app.device"));
        capabilities.setCapability("platformVersion",Constants.appDetails.get("app.version"));
        capabilities.setCapability("platformName", Constants.appDetails.get("app.platform"));
        capabilities.setCapability("isRealMobile", true);
        //AppURL (Create from Wikipedia.apk sample in project)
        capabilities.setCapability("app", "lt://APP10160532421666685046951914"); //Enter your app url
        capabilities.setCapability("deviceOrientation", "PORTRAIT");
        capabilities.setCapability("console", true);
        capabilities.setCapability("network", false);
        capabilities.setCapability("visual", true);
        capabilities.setCapability("devicelog", true);

        String hub = "https://" + Constants.appDetails.get("lambda.user") + ":" + Constants.appDetails.get("lambda.accesskey")+gridURL;
        driver = new AppiumDriver(new URL(hub), capabilities);
    }

}
