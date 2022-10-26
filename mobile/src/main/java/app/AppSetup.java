package app;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import utils.Constants;

import java.io.File;
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
        capabilities.setCapability("build","Android");
        capabilities.setCapability("name","EBANQ");
        capabilities.setCapability("deviceName", Constants.appDetails.get("app.device"));
        capabilities.setCapability("platformVersion",Constants.appDetails.get("app.version"));
        capabilities.setCapability("platformName", Constants.appDetails.get("app.platform"));
        capabilities.setCapability("isRealMobile", true);
        //AppURL (Create from Wikipedia.apk sample in project)
        capabilities.setCapability("app", "lt://APP10160521021666780099488682"); //Enter your app url
        capabilities.setCapability("deviceOrientation", "PORTRAIT");
        capabilities.setCapability("console", true);
        capabilities.setCapability("network", false);
        capabilities.setCapability("visual", true);
        capabilities.setCapability("devicelog", true);

        String hub = "https://" + Constants.appDetails.get("lambda.user") + ":" + Constants.appDetails.get("lambda.accesskey")+gridURL;
        driver = new AppiumDriver(new URL(hub), capabilities);
    }

    public static void launch() throws Exception {
        try {
            File classpathRoot = new File(System.getProperty("user.dir"));
            File appDir = new File(classpathRoot, "/resources/");
            File app = new File(appDir, "EBANQ_2.4.0.apk");
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("deviceName", "emulator-5554");
            capabilities.setCapability("platformVersion", "12");
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("app", app.getAbsolutePath());
            capabilities.setCapability("appPackage", "com.ebanq.Ebanq");
            capabilities.setCapability("automationName", "uiautomator2");
            //capabilities.setCapability("appActivity", "com.amazon.mShop.home.HomeActivity");

            driver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

        }catch(Exception Ex){
            throw new Exception(String.valueOf(Ex.getStackTrace()));
        }
    }

}
