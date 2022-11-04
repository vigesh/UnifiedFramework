package testCases;

import DBUtil.DBHelper;
import extentreports.ExtentTestManager;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import listeners.TestListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.Constants;

import java.lang.reflect.Method;
import java.sql.SQLException;

@Listeners({TestListener.class})
@Epic("Database Test")
@Feature("User DB")

public class DBTest extends BaseTest{

        @Test(priority = 1)
        @Description("Perform a DB Testing")
        public void performReadOperation(Method method) throws SQLException, ClassNotFoundException {
            Constants.testName = method.getName();
            ExtentTestManager.startTest(Constants.testName, "Run the test on Employee table");
            //DataReader.readTestData("Testdata", Constants.testName);
            DBHelper.getUser("1");DBHelper.getUser("2");
            DBHelper.getUser("3");DBHelper.getUser("4");
        }

    @Test(priority = 2)
    @Description("Perform a DB Testing")
    public void performUpdateOperation(Method method) throws SQLException, ClassNotFoundException {
        Constants.testName = method.getName();
        ExtentTestManager.startTest(Constants.testName, "Run the test on Employee table");
        //DataReader.readTestData("Testdata", Constants.testName);
        DBHelper.updateUser("2","userAddress","Kolkatta");
    }
    }
