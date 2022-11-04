package testCases;

import Browsers.BrowserSetup;
import extentreports.ExtentTestManager;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import listeners.TestListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.Constants;

import java.lang.reflect.Method;

@Listeners({TestListener.class})
@Epic("Security Test")
@Feature("Login Page")
public class SecurityTest extends BaseTest {

    @Test(priority = 1)
    @Description("Perform a Security test using ZAP Client")
    public void runSecurityTesting(Method method) throws Throwable {
        Constants.testName = method.getName();
        ExtentTestManager.startTest(Constants.testName, "Run the ZAP Security test on Ebanq App");
        //DataReader.readTestData("Testdata", Constants.testName);
        BrowserSetup.openSecurityBrowser();
        getLoginPage().appLaunch();
        BrowserSetup.generateZapReport();
        
    }

}