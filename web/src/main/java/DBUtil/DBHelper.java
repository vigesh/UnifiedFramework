package DBUtil;

import io.qameta.allure.Step;
import utils.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHelper {
    @Step("Get the username for the userID: {0}")
    public static void getUser(String userId) throws SQLException, ClassNotFoundException {
        String dbURL = Constants.DBURL;
        String username = Constants.DBUser;
        String password = Constants.DBPassword;
        //Load MySQL JDBC Driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        //Creating connection to the database
        Connection con = DriverManager.getConnection(dbURL,username,password);
        //Creating statement object
        Statement st = con.createStatement();
        String selectQuery = "SELECT * FROM employee WHERE userId="+userId;
        //Executing the SQL Query and store the results in ResultSet
        ResultSet rs = st.executeQuery(selectQuery);
        //While loop to iterate through all data and print results
        while (rs.next()) {
            System.out.println("The user name for Id: "+userId+" is "+rs.getString("userName"));
        }
        //Closing DB Connection
        con.close();
    }

    @Step("Update the {1} for the userID: {0} with {2}")
    public static void updateUser(String userId, String variable, String value) throws SQLException, ClassNotFoundException {
        String dbURL = Constants.DBURL;
        String username = Constants.DBUser;
        String password = Constants.DBPassword;
        //Load MySQL JDBC Driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        //Creating connection to the database
        Connection con = DriverManager.getConnection(dbURL,username,password);
        //Creating statement object
        Statement st = con.createStatement();
        String updateQuery = "update employee set "+variable+"='"+value+"'"+" WHERE userId="+userId;
        //Executing the SQL Query and store the results in ResultSet
        st.executeUpdate(updateQuery);
        String selectQuery = "SELECT * FROM employee WHERE userId="+userId;
        //Executing the SQL Query and store the results in ResultSet
        ResultSet rs = st.executeQuery(selectQuery);
        //While loop to iterate through all data and print results
        while (rs.next()) {
            System.out.println("The "+variable+" updated for Id: "+userId+" is "+rs.getString("userAddress"));
        }
        //Closing DB Connection
        con.close();
    }
}
