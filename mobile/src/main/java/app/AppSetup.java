package app;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.remote.DesiredCapabilities;
import utils.Constants;

import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class AppSetup {

    public static ThreadLocal<AppiumDriver> driver = new ThreadLocal<AppiumDriver>();
    public static String gridURL = "@mobile-hub.lambdatest.com/wd/hub";

    public static AppiumDriver getDriver() {
        return driver.get();
    }

    public static void appLaunch(String device, String platform, String version) throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        try {

            if (Constants.appDetails.get("runmode").equalsIgnoreCase("lambda")) {
                capabilities.setCapability("build", "EBANQ App");
                capabilities.setCapability("name", "EBANQ");
                capabilities.setCapability("deviceName", device);
                capabilities.setCapability("platformVersion", version);
                capabilities.setCapability("platformName", platform);
                capabilities.setCapability("isRealMobile", true);
                //AppURL (Create from Wikipedia.apk sample in project)
                capabilities.setCapability("app", "lt://APP10160521021666780099488682"); //Enter your app url
                capabilities.setCapability("deviceOrientation", "PORTRAIT");
                capabilities.setCapability("console", true);
                capabilities.setCapability("network", false);
                capabilities.setCapability("visual", true);
                capabilities.setCapability("devicelog", true);

                String hub = "https://" + Constants.appDetails.get("lambda.user") + ":" + Constants.appDetails.get("lambda.accesskey") + gridURL;
                if(platform.equalsIgnoreCase("ios")){
                    driver.set(new IOSDriver(new URL(hub), capabilities));
                }
                else{
                    driver.set(new AndroidDriver(new URL(hub), capabilities));
                }

            }
            else if (Constants.appDetails.get("runmode").equalsIgnoreCase("local")) {
                File classpathRoot = new File(System.getProperty("user.dir"));
                File appDir = new File(classpathRoot, "/resources/");
                File app = new File(appDir, "EBANQ_2.4.0.apk");
                capabilities.setCapability("deviceName", device);
                capabilities.setCapability("appium:platformVersion", version);
                capabilities.setCapability("appium:platformName", MobilePlatform.ANDROID);
                capabilities.setCapability("appium:app", app.getAbsolutePath());
                capabilities.setCapability("appPackage", "com.ebanq.Ebanq");
                capabilities.setCapability("automationName", "appium");
                capabilities.setCapability("appActivity", "com.ebanq.Ebanq.MainActivity");

                driver.set(new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities));

            }
            driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        } catch (Exception ex) {
            throw new Exception("App Launch failure on runmode: " + Constants.appDetails.get("runmode") + " " + ex);
        }

    }

}
