package pages;

import com.aventstack.extentreports.Status;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;
import utils.Constants;
import utils.Helper;

import static extentreports.ExtentTestManager.getTest;

public class Transfers extends Helper {

    By tabTransferBetweenAccounts= By.xpath("//div[text()='Transfer Between Accounts']");
    By drpdwnDebitAccount=By.xpath("//*[@formcontrolname='accountFrom']");
    By drpdwnCreditAccount=By.xpath("//*[@formcontrolname='accountTo']");
    By txtAmountToTransfer=By.xpath("//*[@title='Outgoing Amount']");
    By txtDescription=By.xpath("//*[@id='description']");
    By btnContinue=By.xpath("//*[normalize-space()='Continue']");
    By btnConfirm=By.xpath("//*[normalize-space()='Confirm']");
    By lblPopupMessage=By.xpath("//div[@class='popup-message']");//Your request has been sent for approval. Request ID #51
    By lnkBackToTransfers=By.xpath("//a[text()='Back to transfers']");

    @Step("Verify User in Transfers Page")
    public Transfers verifyUserInTransferPage(){
        waitForElement(tabTransferBetweenAccounts);
        Assert.assertTrue(driver.getCurrentUrl().contains("transfer"),"Transfers Page");
        //getTest().log(Status.PASS,"User in Transfers Page");
        return this;
    }
    @Step("Navigate to Transfers between accounts page")
    public Transfers thenNavigateToTransfersBetweenAccountsPage() throws Exception {
        click(tabTransferBetweenAccounts,"Transfers between accounts Page");
        waitForPageLoad();
        idleWait(3000);
        getTest().log(Status.PASS,"User in Transfers between Accounts Page");
        return this;
    }
    @Step("Perform Transfer Between Accounts")
    public Transfers andPerformTransfersBetweenAccounts() throws Exception {
        idleWait(5000);
        selectAccountDropdown(drpdwnDebitAccount, "EBQ11113487654","Debit Account");
        selectAccountDropdown(drpdwnCreditAccount, "EBQ11223487456","Credit Account");
        writeText(txtAmountToTransfer,"200");
        writeText(txtDescription,"Monthly EMI on Loan");
        click(btnContinue,"Continue");
        waitForPageLoad();
        click(btnConfirm,"Confirm");
        waitForPageLoad();
        Assert.assertTrue(readText(lblPopupMessage,"Popup Message").contains("Your request has been sent for approval"));
        getTest().log(Status.PASS,"Amount Transfer between Accounts");
        return this;
    }
    @Step("Verify User in Transfer Between Accounts Page")
    public Transfers verifyUserInTransferBetweenAccountsPage(){
        waitForElement(btnContinue);
        Assert.assertTrue(driver.getCurrentUrl().contains("transfer-between-accounts"));
        //getTest().log(Status.PASS,"User in Transfer between accounts Page");
        return this;
    }
}
