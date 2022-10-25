package pages;

import com.aventstack.extentreports.Status;
import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import utils.Constants;
import utils.Helper;

import static extentreports.ExtentTestManager.getTest;

public class Login extends Helper {
    public static Logger Log =  Logger.getLogger(Login.class);

    /**
     * Web Elements
     */
    By txtUsernameOrEmail = By.xpath("//input[@type='email']");
    By txtPassword = By.xpath("//input[@type='password']");
    By btnSignIn = By.xpath("//button[@type='submit']");

    /**
     * Page Methods
     */
    @Step("Invoking the App")
    public Login appLaunch() throws Exception {
        driver.get(Constants.appDetails.get("appURL"));
        getTest().log(Status.PASS,"Invoke the app");
        return this;
    }
    @Step("Login in user: {0}")
    public Login doLogin(String username, String password) throws Exception {
        writeText(txtUsernameOrEmail, username);
        writeText(txtPassword, password);
        click(btnSignIn, "Sign In");
        waitForPageLoad();

        return this;
    }


}