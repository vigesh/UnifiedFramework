package utils;

import app.AppSetup;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;

public class Helper {
    protected AppiumDriver driver;
    public WebDriverWait wait;
    ExcelUtility excel = new ExcelUtility();
    public SoftAssert softAssert=new SoftAssert();


    //Constructor
    public Helper() {
        this.driver = AppSetup.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    protected void waitAndClick(By by) throws Exception {
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(by)).click();
            }catch (Exception ex){
            throw new Exception("Performing click action on element is failed: "+ex.toString());
            }
        }

    protected void click(By by) throws Exception {
        try{
            waitAndFindElement(by).click();
        }catch (Exception ex){
            throw new Exception("Performing click action on element is failed: "+ex);
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

    protected WebElement waitAndFindElement(By by) throws Exception {
        try{
            return  wait.until(ExpectedConditions.elementToBeClickable(by));

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

    public String getText(By by) throws Exception {
        String txt = null;
        switch(Constants.platform) {
            case "Android":
                txt = getAttribute(by, "text");
                break;
            case "iOS":
                txt = getAttribute(by, "label");
                break;
        }
        return txt;
    }
    public void sendKeys(By by, String txt) throws Exception {
        waitAndFindElement(by).sendKeys(txt);
    }

    public void sendKeys(By by, String txt, String msg) throws Exception {
        waitAndFindElement(by).sendKeys(txt);
    }

    public String getAttribute(By by, String attribute) throws Exception {
        return waitAndFindElement(by).getAttribute(attribute);
    }
    public void waitForVisibility(By by) throws Exception {
        wait.withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
}