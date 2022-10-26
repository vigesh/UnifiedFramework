package testCases;

import extentreports.ExtentTestManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import listeners.TestListener;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import testDataUtil.DataReader;
import utils.Constants;

import java.lang.reflect.Method;
import java.net.URL;

@Listeners({TestListener.class})
@Epic("Regression Test")
@Feature("Corporate Account")
public class FunctionalTest extends BaseTest {

    @Test
    @Description("Verify the Account login")
    public void verifyAccountLogin(Method method) throws InterruptedException {
        Constants.testName = method.getName();
        ExtentTestManager.startTest(Constants.testName, "Verify the account login");
        DataReader.readTestData("Testdata", Constants.testName);
        getLogin().doAppLogin();
    }



}