package testDataUtil;

import com.aventstack.extentreports.Status;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import logs.Log;
import utils.Constants;

import java.util.Map;

import static extentreports.ExtentTestManager.getTest;

public class DataReader {

    public static Map<String, String> readTestData(String filename, String testMethod){
        String testDataFile=System.getProperty("user.dir")+"\\resources\\"+filename+".xls";
        String dataQuery="select * from data where Test_case_name='"+testMethod+"'";
        Fillo f=new Fillo();
        Connection con=null;
        Recordset rs=null;
        try{
            con=f.getConnection(testDataFile);
            rs=con.executeQuery(dataQuery);

            while(rs.next()){
                for(int i=0;i<rs.getFieldNames().size();i++){
                    String colName=rs.getField(i).name().toLowerCase();
                    String colValue=rs.getField(i).value();
                    Constants.data.put(colName, colValue);
                }
            }
        } catch (FilloException e) {
            getTest().log(Status.FAIL,"Data Reading have an issue");
        }
        finally {
            rs.close();
            con.close();
        }
        return Constants.data;
    }
}
