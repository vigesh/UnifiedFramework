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
import java.util.HashMap;
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
                HashMap<String, Object> ltOptions = new HashMap<String, Object>();
                ltOptions.put("w3c", true);
                ltOptions.put("name", "EBANQ");
                ltOptions.put("platformName", platform);
                ltOptions.put("deviceName", device);
                ltOptions.put("platformVersion", version);
                ltOptions.put("isRealMobile", true);
                ltOptions.put("app", "lt://APP10160521021666780099488682");
                ltOptions.put("deviceOrientation", "PORTRAIT");
                ltOptions.put("console", true);
                ltOptions.put("network", false);
                ltOptions.put("visual", true);
                ltOptions.put("devicelog", true);//Enter your app url

                capabilities.setCapability("lt:options", ltOptions);

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
