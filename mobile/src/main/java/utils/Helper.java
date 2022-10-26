package utils;

import app.AppSetup;
import com.aventstack.extentreports.Status;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.asserts.SoftAssert;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import static extentreports.ExtentTestManager.getTest;

public class Helper {
    public AppiumDriver driver;
    public WebDriverWait wait;
    ExcelUtility excel = new ExcelUtility();
    public SoftAssert softAssert=new SoftAssert();


    //Constructor
    public Helper() {
        driver= AppSetup.getDriver();
        //wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public WebElement fluentWait(By element){
        Wait<WebDriver> fwait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);

        WebElement ele = fwait.until(ExpectedConditions.presenceOfElementLocated(element));
        return ele;
    }
    //Click Method
    public void click(By by, String element) throws Exception {
        WebElement ele=findElement(by);
        ele.click();
        //ExtentTestManager.getTest().log(Status.INFO,"Clicked on element: "+element);
    }

    //Write Text
    public void writeText(By by, String text) throws Exception {
        WebElement ele=findElement(by);
        ele.sendKeys(text);
        //ExtentTestManager.getTest().log(Status.INFO,"Value entered on element: "+ele.getText()+" with value: "+text);
    }

    //Read Text
    public String readText(By by, String element) throws Exception {
        String retValue=findElement(by).getText();
        getTest().log(Status.INFO,"Reading text on element "+element+ " is: "+retValue);
        return retValue;

    }

    //Get Attribute
    public String readAttribute(By by, String attribute, String elementName) throws Exception {
        WebElement ele=findElement(by);
        //ExtentTestManager.getTest().log(Status.INFO,"Reading Attribute on element: "+elementName);
        return ele.getAttribute(attribute);
    }

    //Wait
    public WebElement waitForElement(By by) {
        wait = new WebDriverWait(driver, TimeUnit.SECONDS.toSeconds(10));
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }
    public WebElement findElement(By byElement) throws InterruptedException {
        WebElement ele= waitForElement(byElement);
        ((JavascriptExecutor)driver).executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", ele);
        idleWait(1000);
        ((JavascriptExecutor)driver).executeScript("arguments[0].setAttribute('style', 'background: transparent; border: 0px solid red;');", ele);
        return ele;
    }

    public boolean waitForElementContainText(By element, String text, int timeout) throws Exception {
        boolean textPresent = false;
        wait = new WebDriverWait(driver, TimeUnit.SECONDS.toSeconds(10));
        textPresent = wait.until(ExpectedConditions.textToBePresentInElement(waitForElement(element), text));
        return textPresent;
    }


    public void idleWait(int waitTime) throws InterruptedException {
        Thread.sleep(waitTime);
    }

    public String generateRandomZipcode() {
        String val;
        Random rd = new Random();
        val = String.valueOf(rd.nextLong()).replaceAll("\\W", "");
        val = val.substring(0, 5);
        return val;
    }

    public String generateRandomNumber() {

        Random rd = new Random();
        return String.valueOf(rd.nextLong()).replaceAll("\\W", "");
    }

    public String generateRandomPhoneNumber() {
        String val;
        Random rd = new Random();
        val = String.valueOf(rd.nextLong()).replaceAll("\\W", "");
        val = val.substring(0, 10);
        return val;
    }

    public String generateRandomSSN() {
        String val;
        Random rd = new Random();
        val = String.valueOf(rd.nextLong()).replaceAll("\\W", "");
        val = val.substring(0, 6);
        val = "666" + val;
        return val;
    }

    public static String generateRandomIntegrationID() {
        String val;
        Random rd = new Random();
        val = String.valueOf(rd.nextLong()).replaceAll("\\W", "");
        val = val.substring(0, 6);
        return val;
    }

    public String selectRandomDropdownValue(By element) throws Exception {
        String selectedDropDownValue = null;
        Select dropDown = new Select(waitForElement(element));
        Random rand = new Random();
        int list = rand.nextInt(dropDown.getOptions().size());
        if (list == 0) {
            list += 2;
        }
        dropDown.selectByIndex(list);
        //pauseWait(1000);
        selectedDropDownValue = dropDown.getFirstSelectedOption().getText().trim();
        waitForElement(element);


        return selectedDropDownValue;
    }

    public void waitForPageLoad() throws Exception {
        for (int i = 0; i < 10; i++) {

            wait = new WebDriverWait(driver, TimeUnit.SECONDS.toSeconds(10));
            wait.until((ExpectedCondition<Boolean>) wdriver -> ((JavascriptExecutor) driver).executeScript(
                    "return document.readyState").equals("complete"));
        }
    }

    public String selectDropdownValue(By element, String value, String... text) throws Exception {
        String selectedDropDownValue = null;
        Select dropDown = new Select(waitForElement(element));
        dropDown.selectByVisibleText(value);
        idleWait(1000);
        selectedDropDownValue = dropDown.getFirstSelectedOption().getText().trim();
        return selectedDropDownValue;
    }

    public void focusWebElement(By element) throws Exception {

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: \"smooth\", block: \"center\", inline: \"nearest\"});", waitForElement(element));
    }

    public void pageScrollToBottom() throws Exception {

        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

//    public List<WebElement> elements(By element) {
//        List ellist = driver.findElements(element);
//        return ellist;
//    }

    public void jsClick(By element) throws Exception {

        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", findElement(element));
    }
    public List<WebElement> listOfElements(By element) throws InterruptedException {
        List<WebElement> elementList=findElement(element).findElements(element);
        return elementList;
    }
//    public String takePassScrenshot(String description) throws IOException {
//        TakesScreenshot ts = driver;
//        File source = ts.getScreenshotAs(OutputType.FILE);
//        String dest = System.getProperty("user.dir") +"\\extent-reports\\Screenshots\\"+ Constants.testName+"_"+System.currentTimeMillis()+".png";
//        File destination = new File(dest);
//        FileUtils.copyFile(source, destination);
//        //ExtentTestManager.getTest().log(Status.PASS,description, ExtentTestManager.getTest().addScreenCaptureFromPath(dest).getModel().getMedia().get(0));
//        return dest;
//    }
    /*public String takeFullScreenshot() throws IOException {
        Screenshot s=new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
        ImageIO.write(s.getImage(),"PNG",new File(System.getProperty("user.dir")+"//extent-reports//Screenshots//"+"Image"+System.currentTimeMillis()+".png"));

    }*/

    public void selectAccountDropdown(By by, String dropdownValue, String element) throws Exception {
        click(by,element);
        idleWait(500);
        List<WebElement>elements=listOfElements(By.xpath("//span[text()='"+dropdownValue+"']"));
        if(elements.size()>=1){
            click(By.xpath("//span[text()='"+dropdownValue+"'][1]"),element);
        }
    }


}