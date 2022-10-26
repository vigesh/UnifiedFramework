package pages;

import io.appium.java_client.MobileElement;
import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import utils.Constants;
import utils.Helper;

public class Login extends Helper {
    public static Logger Log =  Logger.getLogger(Login.class);

    /**
     * Web Elements
     */
    By txtUsernameOrEmail = By.xpath("//input[@type='email']");
    By txtPassword = By.xpath("//input[@type='password']");
    By btnSignIn = By.xpath("//button[@type='submit']");

   @Step
    public void doAppLogin() {
       MobileElement txtUsername = (MobileElement) driver.findElementByClassName("(com.ebanq.Ebanq:android.widget.EditText)[1]");
       txtUsername.sendKeys(Constants.data.get("username"));

       MobileElement txtPassword = (MobileElement) driver.findElementByClassName("com.ebanq.Ebanq:android.widget.EditText[2]");
       txtPassword.sendKeys(Constants.data.get("password"));

       MobileElement btnLogin = (MobileElement) driver.findElementByClassName("com.ebanq.Ebanq:android.widget.Button");
       btnLogin.click();

       //Thread.sleep(10000);
   }

}