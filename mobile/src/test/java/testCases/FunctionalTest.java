package testCases;

import app.AppSetup;
import extentreports.ExtentTestManager;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import listeners.TestListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.Login;
import testDataUtil.DataReader;
import utils.Constants;

import java.lang.reflect.Method;

@Listeners({TestListener.class})
@Epic("Regression Test")
@Feature("Corporate Account")
public class FunctionalTest extends BaseTest {

    @Test
    @Description("Verify the Account login")
    public void verifyAccountLogin(Method method) throws Exception {
        Constants.testName = method.getName();
        ExtentTestManager.startTest(Constants.testName, "Verify the account login");
        DataReader.readTestData("Testdata", Constants.testName);

        Login login=new Login(AppSetup.driver);
        login.doAppLogin();
    }



}