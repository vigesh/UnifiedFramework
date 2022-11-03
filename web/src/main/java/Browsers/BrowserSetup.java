package Browsers;

import io.github.bonigarcia.wdm.WebDriverManager;
import logs.Log;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.zaproxy.clientapi.core.ApiResponse;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;
import utils.Constants;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class BrowserSetup {

    public static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();
    public static ClientApi api;
    public static Proxy proxy;

    public static void openBrowser(String browser, String os, String version) throws MalformedURLException {
        if (Constants.appDetails.get("runmode").equalsIgnoreCase("local")) {
            switch (browser) {
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
        } else if (Constants.appDetails.get("runmode").equalsIgnoreCase("lambda")) {
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

    public static void generateZapReport() {
        String title = "ZAP Security Report";
        String reportFileName = "ZAPSecurityReport_" + System.currentTimeMillis();
        String template = "traditional-html";
        String reportdir = System.getProperty("user.dir") + "\\ZapReport\\";
        String description = "This is the ZAP Security Report";
        try {
            ApiResponse response = api.reports.generate(title, template, null, description, null, null, null, null, null, reportFileName,
                    null, reportdir, null);
            System.out.println("ZAP report can be found in :" + response.toString());
        } catch (ClientApiException e) {
            throw new RuntimeException(e);
        }
    }
        public static void openSecurityBrowser () {

            proxy = new Proxy();
            proxy.setHttpProxy(Constants.ZAP_PROXY_SERVER);
            proxy.setSslProxy(Constants.ZAP_PROXY_SERVER);
            ChromeOptions co = new ChromeOptions();
            co.setProxy(proxy);
            co.setAcceptInsecureCerts(true);
            WebDriverManager.chromedriver().setup();
            if(driver!=null){
                driver.get().quit();
            }
            driver.set(new ChromeDriver(co));
            api = new ClientApi(Constants.ZAP_PROXY_ADDRESS, Constants.ZAP_PROXY_PORT, Constants.ZAP_API_KEY);
        }


}
