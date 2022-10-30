package pages;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;
import utils.Constants;
import utils.Helper;

public class Login extends Helper {
    public Login(AndroidDriver<MobileElement> driver) {
        super(driver);

    }
      /**
     * Web Elements
     */
    By txtUsername = By.xpath("//android.view.View[1]/android.view.View/android.widget.EditText");
    By txtPassword = By.xpath("//android.view.View[2]/android.view.View/android.widget.EditText");
    By btnLogin = By.xpath("//android.widget.Button[@text='Login']");
    By firstclick = By.xpath("//*[@resource-id='android:id/content']");
    By hamburgerIcon=By.xpath("//android.view.View[@resource-id='nav']//android.widget.Button");
    By tabTransfers=By.xpath("//android.view.View[@text='Transfers']");
    By tabTransfersBetweenAccounts=By.xpath("//android.view.View[@text='Transfer Between Accounts']");
    By drpdwnDebitFrom=By.xpath("//android.view.View[@text='Debit from *']/../../following-sibling::android.widget.Button");
    By drpdownCreditTo=By.xpath("//android.view.View[@text='Credit to *']/../../following-sibling::android.widget.Button");
    By txtAmountToTransfer=By.xpath("//android.view.View[2]/android.widget.EditText");
    By btnContinue=By.xpath("//android.widget.Button[@text='Continue']");
    By btnConfirm=By.xpath("//android.view.View[@resource-id='nav']//android.widget.Button[@text='Confirm']");
    By lblAckMsg=By.xpath("//android.view.View[contains(@text,'Your request has been sent for approval')]");

    @Step
    public void doAppLogin() throws Exception {
        try {
            click(firstclick);
            Thread.sleep(2000);
            sendKey(txtUsername, Constants.data.get("username"));
            sendKey(txtPassword, Constants.data.get("password"));
            click(btnLogin);
            handleSync(3000);
        } catch (Exception E) {
            throw new Exception(String.valueOf(E.getCause()));
        }
    }
    @Step
    public void pinSetForLogin() throws Exception {
         pinSelection("1");pinSelection("2");pinSelection("3");
            pinSelection("4");pinSelection("5");
            handleSync(2000);
            pinSelection("1");pinSelection("2");pinSelection("3");
            pinSelection("4");pinSelection("5");
    }

    @Step
    public void accountTransferBetweenAccounts() throws Exception {

            waitAndClick(hamburgerIcon);
            waitAndClick(tabTransfers);
            handleSync(2000);
            click(tabTransfersBetweenAccounts);
            handleSync(2000);
            click(drpdwnDebitFrom);
            By debitAccountNumber = By.xpath("//android.widget.Button[contains(@text,'EBQ11113487654')]");
            click(debitAccountNumber);
            click(drpdownCreditTo);
            By creditAccountNumber=By.xpath("//android.widget.Button[contains(@text,'EBQ11223387654')]");
            click(creditAccountNumber);
            sendKey(txtAmountToTransfer,"200");
            click(btnContinue);
            handleSync(3000);
            click(firstclick);
            click(btnConfirm);
            handleSync(2000);
            Assert.assertTrue(waitAndFindElement(lblAckMsg).isDisplayed());
    }

}