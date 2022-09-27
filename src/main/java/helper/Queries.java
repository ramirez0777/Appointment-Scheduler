package helper;

import c195.c195.Customer;
import main.Customers;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Queries {

    public static boolean verifyLogin(String username, String password) throws SQLException {
        boolean verified = false;
        JDBC.openConnection();
        String sql = "SELECT * FROM client_schedule.users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet results = ps.executeQuery();

        while(results.next()){
            if(username.equals(results.getString("User_Name"))){
                if(password.equals(results.getString("Password"))){
                    verified = true;
                    break;
                }
            }
        }

        JDBC.closeConnection();
        return verified;
    }

    public static void gatherCustomers() throws SQLException{
        JDBC.openConnection();
        String sql = "SELECT * from client_schedule.customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet results = ps.executeQuery();

        while(results.next()){
            Customer currentCustomer = new Customer(results.getInt("Customer_ID"), results.getString("Customer_Name"), results.getString("Address"), results.getInt("Division_ID"), results.getString("Postal_Code"), results.getString("Phone"));
            Customers.addCustomer(currentCustomer);
        }
        JDBC.closeConnection();
    }
}
