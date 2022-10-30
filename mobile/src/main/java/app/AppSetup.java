package app;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import utils.Constants;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class AppSetup {

    public static ThreadLocal<AndroidDriver> driver = new ThreadLocal<>();
    public static String gridURL = "@mobile-hub.lambdatest.com/wd/hub";

    public static AndroidDriver getDriver() {
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
                driver.set(new AndroidDriver<MobileElement>(new URL(hub), capabilities));
            }
            else if (Constants.appDetails.get("runmode").equalsIgnoreCase("local")) {
                File classpathRoot = new File(System.getProperty("user.dir"));
                File appDir = new File(classpathRoot, "/resources/");
                File app = new File(appDir, "EBANQ_2.4.0.apk");
                capabilities.setCapability("deviceName", "emulator-5556");
                capabilities.setCapability("platformVersion", "11");
                capabilities.setCapability("platformName", "Android");
                capabilities.setCapability("app", app.getAbsolutePath());
                capabilities.setCapability("appPackage", "com.ebanq.Ebanq");
                capabilities.setCapability("automationName", "appium");
                capabilities.setCapability("appActivity", "com.ebanq.Ebanq.MainActivity");

                driver.set(new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities));

            }
            driver.get().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        } catch (Exception ex) {
            throw new Exception("App Launch failure on runmode: " + Constants.appDetails.get("runmode") + " " + ex.toString());
        }

    }

}
