package pages;

import com.aventstack.extentreports.Status;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;
import utils.Helper;

import static extentreports.ExtentTestManager.getTest;

public class Dashboard extends Helper {
    private String currentBalance;
    By lblWelcomeText= By.xpath("//span[contains(text(),'Hello')]");
    By drpdwnAccountId=By.xpath("//*[@formcontrolname='accountId']");
    By lblCurrentBalance=By.xpath("//div[text()='Current Balance']/following-sibling::div[1]");
    By lblStatus=By.xpath("//div[text()='Status']/following-sibling::div[1]");
    By lblCurrency=By.xpath("//div[text()='Currency']/following-sibling::div[1]");
    By tabTransfers=By.xpath("//label[text()='Transfers']");
    By btnLogOut=By.xpath("//span[text()='Log Out']");

    @Step("Verify user logged in and landing on Dashboard")
    public Dashboard verifyUserInDashboard() throws Exception {
        readText(lblWelcomeText, "Welcome Text");
        getTest().log(Status.PASS,"Verify User in Dashboard");
        return this;
    }
    @Step("Select Account Number in Dashboard")
    public Dashboard selectAccountNumber() throws Exception {
        selectAccountDropdown(drpdwnAccountId, "EBQ11113487654","Account Number");
        getTest().log(Status.PASS,"Select Account Number");
        return this;
    }
    @Step("Verify Current Balance")
    public Dashboard andVerifyCurrentBalance() throws Exception {
        currentBalance=readText(lblCurrentBalance, "Current Balance");
        getTest().log(Status.PASS,"Current Account Balance in Account Number EBQ11113487654 is: "+currentBalance);
        return this;
     }

    @Step("Verify Account status Active")
    public Dashboard andVerifyAccountStatusInActive() throws Exception {
        Assert.assertTrue(readText(lblStatus,"Status").equalsIgnoreCase("active"),"Status in Active");
        getTest().log(Status.PASS,"Account status is Active");
        return this;
    }

    @Step("Verify Account Currency is Euro")
    public Dashboard andVerifyAccountCurrencyIsEuro() throws Exception {
        Assert.assertTrue(readText(lblCurrency,"Currency").equalsIgnoreCase("EUR"),"Currency is Euro");
        getTest().log(Status.PASS,"Account Currency is EURO");
        return this;
    }
    @Step("Navigate to Transfers Page")
    public Dashboard thenNavigateToTransfersPage() throws Exception {
        click(tabTransfers,"Transfers Page");
        waitForPageLoad();
        idleWait(3000);
        getTest().log(Status.PASS,"User in Transfers Page");
        return this;
    }

    @Step("Click on Logout")
    public Dashboard doLogout() throws Exception {
        click(btnLogOut, "Logout");
        idleWait(1000);
        getTest().log(Status.PASS,"User Logged out");
        return this;
    }
}
