package pages;

import app.AppSetup;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
    @AndroidFindBy(xpath = "//*[@class='android.widget.EditText'][1]")
    public AndroidElement txtUsername;
    @AndroidFindBy(xpath = "//*[@class='android.widget.EditText'][2]")
    public AndroidElement txtPassword;
    @AndroidFindBy(xpath = "//*[@class='android.widget.Button']")
    public AndroidElement btnLogin;

    @Step
    public void doAppLogin() throws Exception {
        try{
//            txtUsername.sendKeys(Constants.data.get("username"));
//            txtPassword.sendKeys(Constants.data.get("password"));
//            btnLogin.click();

        WebElement txtUsername = driver.findElementByXPath("//*[@class='android.widget.EditText'][1]");
        txtUsername.sendKeys(Constants.data.get("username"));

            WebElement txtPassword =  driver.findElementByXPath("//*[@class='android.widget.EditText'][2]");
        txtPassword.sendKeys(Constants.data.get("password"));

        WebElement btnLogin =  driver.findElementByXPath("//*[@class='android.widget.Button']");
        btnLogin.click();
        } catch(Exception E){
            throw new Exception(E.getMessage());
        }




   }

}