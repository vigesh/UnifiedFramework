package pages;

import com.aventstack.extentreports.Status;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.*;



import java.util.List;

import static extentreports.ExtentTestManager.getTest;

public class Login extends Helper {
//    public Login(AppiumDriver driver) {
//        super(driver);
//
//    }
    By txtUsername = By.xpath("//android.view.View[1]/android.view.View/android.widget.EditText");
    By txtPassword = By.xpath("//android.view.View[2]/android.view.View/android.widget.EditText|//android.widget.EditText[@text='Enter password']");
    By btnLogin = By.xpath("//android.widget.Button[@text='Login']");
    By firstclick = By.xpath("//*[@resource-id='android:id/content']");
    By secondclick = By.xpath("//*[@resource-id='android:id/content']/android.webkit.WebView");
    By hamburgerIcon = By.xpath("//android.view.View[@resource-id='nav']//android.widget.Button");
    By tabTransfers = By.xpath("//android.view.View[@text='Transfers']");
    By tabTransfersBetweenAccounts = By.xpath("//*[@text='Transfer Between Accounts']");
    By drpdwnDebitFrom = By.xpath("//*[@text='Debit from *']/../../following-sibling::android.widget.Button");
    By drpdownCreditTo = By.xpath("//*[@text='Credit to *']/../../following-sibling::android.widget.Button");
    By txtAmountToTransfer = By.xpath("//android.view.View[2]/android.widget.EditText|//*[contains(@text,'Amount to transfer')]/following-sibling::android.view.View//android.widget.EditText");
    By btnContinue = By.xpath("//android.widget.Button[@text='Continue']|//android.widget.Button[@text='Confirm']");
    By btnConfirm = By.xpath("//android.widget.Button[@text='Confirm']|//*[@resource-id='nav']//android.widget.Button[@text='Confirm']");
    By lblAckMsg = By.xpath("//*[contains(@text,'Your request has been sent for approval')]");

    @Step("Login the app with valid credentials")
    public Login doAppLogin() throws Exception {
        try {
            click(firstclick);
            Thread.sleep(2000);
            sendKey(txtUsername, Constants.data.get("username"));
            sendKey(txtPassword, Constants.data.get("password"));
            click(btnLogin);
            handleSync(3000);
            getTest().log(Status.PASS, "Login Successful");
        } catch (Exception E) {
            getTest().log(Status.FAIL, "Login Failed");
            throw new Exception(String.valueOf(E.getCause()));
        }
        return this;
    }

    @Step("Pin setup for the next login")
    public Login pinSetForLogin() throws Exception {
        pinSelection("1");
        pinSelection("2");
        pinSelection("3");
        pinSelection("4");
        pinSelection("5");
        handleSync(2000);
        pinSelection("1");
        pinSelection("2");
        pinSelection("3");
        pinSelection("4");
        pinSelection("5");
        getTest().log(Status.PASS, "Pin set successful");
        return this;
    }

    @Step("Transfer the amount between accounts")
    public Login accountTransferBetweenAccounts() throws Exception {

        waitAndClick(hamburgerIcon);
        handleSync(2000);
        waitAndClick(tabTransfers);
        handleSync(5000);
        waitAndClick(tabTransfersBetweenAccounts);
        handleSync(2000);
        click(drpdwnDebitFrom);
        By debitAccountNumber = By.xpath("//android.view.View//android.widget.Button[contains(@text,'EBQ11113487654')]|//*[contains(@text,'EBQ11113487654')]");
        click(debitAccountNumber);
        click(drpdownCreditTo);
        By creditAccountNumber = By.xpath("//android.view.View//android.widget.Button[contains(@text,'EBQ11223387654')]|//*[contains(@text,'EBQ11223387654')]");
        click(creditAccountNumber);
        sendKey(txtAmountToTransfer, "200");
        click(btnContinue);
        handleSync(3000);
        click(secondclick);
        handleSync(2000);
        click(btnContinue);
        handleSync(2000);
        Assert.assertTrue(waitAndFindElement(lblAckMsg).isDisplayed());
        getTest().log(Status.PASS, "Accounts Transfer Success");

        return this;
    }
}

