package utils;

import app.AppSetup;
import com.aventstack.extentreports.Status;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.asserts.SoftAssert;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import static extentreports.ExtentTestManager.getTest;

public class Helper {
    protected AndroidDriver<MobileElement> driver;
    public WebDriverWait wait;
    ExcelUtility excel = new ExcelUtility();
    public SoftAssert softAssert=new SoftAssert();


    //Constructor
    public Helper(AndroidDriver<MobileElement> driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 15);
    }

    protected void waitAndClick(By by) throws Exception {
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(by)).click();
            }catch (Exception ex){
            throw new Exception(ex.toString());
            }
        }

    protected void click(By by) throws Exception {
        try{
            waitAndFindElement(by).click();
        }catch (Exception ex){
            throw new Exception(ex.toString());
        }
    }

    protected void hideKeyboard() { driver.navigate().back(); }

    protected List<WebElement> waitAndFindElements(By by) throws Exception {
        try{
            return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));

        }catch (Exception ex){
            throw new Exception(ex.toString());
        }
    }

    protected MobileElement waitAndFindElement(By by) throws Exception {
        try{
            return (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(by));

        }catch (Exception ex){
            throw new Exception(ex.toString());
        }
    }

    protected String getText(By by) throws Exception {
        try{
            return waitAndFindElement(by).getText();

        }catch (Exception ex){
            throw new Exception(ex.toString());
        }
    }

    protected void sendKey(By by, String text) throws Exception {
        try{
            waitAndFindElement(by).sendKeys(text);
        }catch (Exception ex){
            throw new Exception(ex.toString());
        }
    }

    protected void pinSelection(String numericVal) throws Exception {
        try{
            By btnPin=By.xpath("//android.widget.Button[@text='"+numericVal+"']");
            click(btnPin);
        }catch (Exception ex){
            throw new Exception(ex.toString());
        }
    }

    protected void handleSync(long seconds) throws InterruptedException {
        Thread.sleep(seconds);
    }
}