package testCases;

import extentreports.ExtentTestManager;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import listeners.TestListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import testDataUtil.DataReader;
import utils.Constants;
import java.lang.reflect.Method;

@Listeners({TestListener.class})
@Epic("Regression Test")
@Feature("Corporate Account")
public class FunctionalTest extends BaseTest {

    @Test(priority = 1)
    @Description("Verify the Account login")
    public void verifyAccountLogin(Method method) throws Exception {
        Constants.testName = method.getName();
        ExtentTestManager.startTest(Constants.testName, "Verify the account login");
        DataReader.readTestData("Testdata", Constants.testName);
        getLogin().doAppLogin();
    }

    @Test(priority = 2)
    @Description("Pin Set for the app")
    public void doPinSetForTheApp() throws Exception {
    getLogin().pinSetForLogin();

}
    @Test(priority = 3)
    @Description("Do Transfer between accounts")
    public void andVerifyAccountTransferBetweenAccounts() throws Exception {
        getLogin().accountTransferBetweenAccounts();
    }
}