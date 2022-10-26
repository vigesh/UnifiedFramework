package pages;

import app.AppSetup;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import utils.Constants;
import utils.Helper;

public class Login extends Helper {
    public Login(AppiumDriver driver){
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    /**
     * Web Elements
     */
    @AndroidFindBy(id = "com.biforst.broonline.bigbazar:id/username")
    public AndroidElement txtUsername;
    @AndroidFindBy(xpath = "com.biforst.broonline.bigbazar:id/password")
    public AndroidElement txtPassword;
    @AndroidFindBy(xpath = "com.biforst.broonline.bigbazar:id/btn_login")
    public AndroidElement btnLogin;

    @Step
    public void doAppLogin() throws Exception {
        try{
            txtUsername.sendKeys(Constants.data.get("username"));
            txtPassword.sendKeys(Constants.data.get("password"));
            btnLogin.click();
        } catch(Exception E){
            throw new Exception(E.getMessage());
        }


//        MobileElement txtUsername = (MobileElement) driver.findElementByXPath("//*[@class='android.widget.EditText'][1]");
//        txtUsername.sendKeys(Constants.data.get("username"));
//
//        MobileElement txtPassword = (MobileElement) driver.findElementByXPath("//*[@class='android.widget.EditText'][2]");
//        txtPassword.sendKeys(Constants.data.get("password"));
//
//        MobileElement btnLogin = (MobileElement) driver.findElementByXPath("//*[@class='android.widget.Button']");
//        btnLogin.click();

   }

}