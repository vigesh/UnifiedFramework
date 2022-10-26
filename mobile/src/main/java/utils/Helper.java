package utils;

import app.AppSetup;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.asserts.SoftAssert;

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



}