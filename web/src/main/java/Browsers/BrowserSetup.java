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

    public static RemoteWebDriver driver;

    public static void openBrowser() {
        if(Constants.appDetails.get("runmode").equalsIgnoreCase("local")){
            switch (Constants.appDetails.get("browser")){
                case "CHROME":
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driver=new ChromeDriver();
                    break;
                case "IE":
                case "ie":
                    WebDriverManager.iedriver().setup();
                    driver=new InternetExplorerDriver();
                    break;
                case "FIREFOX":
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver=new FirefoxDriver();
                    break;
            }
            driver.manage().window().maximize();
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
        else if(Constants.appDetails.get("runmode").equalsIgnoreCase("lambda")){
            DesiredCapabilities capabilities = new DesiredCapabilities();

            if(Constants.appDetails.get("browser").equalsIgnoreCase("chrome")){
                capabilities.setCapability("browserName", Constants.appDetails.get("browser"));
                capabilities.setCapability("version", Constants.appDetails.get("chrome.version"));
                capabilities.setCapability("platform", Constants.appDetails.get("windows.os"));
            } else if(Constants.appDetails.get("browser").equalsIgnoreCase("firefox")){
                capabilities.setCapability("browserName", Constants.appDetails.get("browser"));
                capabilities.setCapability("version", Constants.appDetails.get("firefox.version"));
                capabilities.setCapability("platform", Constants.appDetails.get("windows.os"));
            }
            capabilities.setCapability("build", "RegressionTest");
            capabilities.setCapability("name", "EBANQ");
            try {
                driver = new RemoteWebDriver(new URL("https://" + Constants.appDetails.get("lambda.user") + ":" + Constants.appDetails.get("lambda.accesskey")
                        + Constants.appDetails.get("gridURL")), capabilities);
            } catch (MalformedURLException e) {
                Log.error("Invalid grid URL");
            } catch (Exception e) {
                Log.error(e.getMessage());
            }
        }

    }
    public static WebDriver getDriver() {
        return driver;
    }

}
