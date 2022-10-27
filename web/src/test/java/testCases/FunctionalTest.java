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

    @Test
    @Description("Verify the current Account balance for the selected Account")
    public void verifyCurrentAccountBalance(Method method) throws Throwable {
        Constants.testName = method.getName();
        ExtentTestManager.startTest(Constants.testName, "Verify the current Account balance for the selected Account");
        DataReader.readTestData("Testdata", Constants.testName);
        getLoginPage().appLaunch().doLogin(Constants.data.get("username"),Constants.data.get("password"));
        getDashboardPage().verifyUserInDashboard().selectAccountNumber().andVerifyCurrentBalance().doLogout();
    }
    @Test(priority = 1)
    @Description("Verify the current Account Status is Active for the selected Account")
    public void veirfyCurrentAccountStatusIsActive(Method method) throws Throwable {
        Constants.testName = method.getName();
        ExtentTestManager.startTest(Constants.testName, "Verify the current Account Status is Active for the selected Account");
        DataReader.readTestData("Testdata", Constants.testName);
        getLoginPage().appLaunch().doLogin(Constants.data.get("username"),Constants.data.get("password"));
        getDashboardPage().verifyUserInDashboard().selectAccountNumber().andVerifyAccountStatusInActive().doLogout();
    }
   @Test(priority = 2)
    @Description("Verify the current Account Currency is Euro for the selected Account")
    public void verifyAccountCurrencyIsEuro(Method method) throws Throwable {
        Constants.testName = method.getName();
        ExtentTestManager.startTest(Constants.testName, "Verify the current Account Currency is Euro for the selected Account");
        DataReader.readTestData("Testdata", Constants.testName);
        getLoginPage().appLaunch().doLogin(Constants.data.get("username"),Constants.data.get("password"));
        getDashboardPage().verifyUserInDashboard().selectAccountNumber().andVerifyAccountCurrencyIsEuro().doLogout();
    }
    @Test(priority = 3)
    @Description("Verify the Amount Transfer between Accounts")
    public void verifyTheTransferBetweenAccounts(Method method) throws Throwable {
        Constants.testName = method.getName();
        ExtentTestManager.startTest(Constants.testName, "Verify the current Account Currency is Euro for the selected Account");
        DataReader.readTestData("Testdata", Constants.testName);
        getLoginPage().appLaunch().doLogin(Constants.data.get("username"),Constants.data.get("password"));
        getDashboardPage().verifyUserInDashboard().thenNavigateToTransfersPage();
        getTransfersPage().verifyUserInTransferPage().thenNavigateToTransfersBetweenAccountsPage().verifyUserInTransferBetweenAccountsPage().andPerformTransfersBetweenAccounts();
        getDashboardPage().doLogout();
    }


}