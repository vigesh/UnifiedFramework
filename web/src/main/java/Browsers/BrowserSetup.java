package Browsers;

import io.github.bonigarcia.wdm.WebDriverManager;
import logs.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.Constants;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class BrowserSetup {

    public static ThreadLocal<RemoteWebDriver> driver=new ThreadLocal<>();

    public static void openBrowser(String browser, String os, String version) throws MalformedURLException {
        if(Constants.appDetails.get("runmode").equalsIgnoreCase("local")){
            switch (browser){
                case "CHROME":
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driver.set(new ChromeDriver());
                    break;
                case "IE":
                case "ie":
                    WebDriverManager.iedriver().setup();
                    driver.set(new InternetExplorerDriver());
                    break;
                case "FIREFOX":
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver.set(new FirefoxDriver());
                    break;
            }
            driver.get().manage().window().maximize();
            driver.get().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
        else if(Constants.appDetails.get("runmode").equalsIgnoreCase("lambda")){
            DesiredCapabilities capabilities = new DesiredCapabilities();

                capabilities.setCapability("browserName", browser);
                capabilities.setCapability("version", version);
                capabilities.setCapability("platform", os);
                capabilities.setCapability("build", "RegressionTest");
                capabilities.setCapability("name", "EBANQ");
            try {
                driver.set(new RemoteWebDriver(new URL("https://" + Constants.appDetails.get("lambda.user") + ":" + Constants.appDetails.get("lambda.accesskey")
                        + Constants.appDetails.get("gridURL")), capabilities));
            } catch (MalformedURLException e) {
                Log.error("Invalid grid URL");
            } catch (Exception e) {
                Log.error(e.getMessage());
            }
        }

    }
    public static WebDriver getDriver() {
        return driver.get();

    }

}
