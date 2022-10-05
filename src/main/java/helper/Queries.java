package helper;

import c195.c195.Appointment;
import c195.c195.Customer;
import c195.c195.LoginScreen;
import c195.c195.ViewCustomerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Customers;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class Queries {

    public static boolean verifyLogin(String username, String password) throws SQLException {
        boolean verified = false;

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


        return verified;
    }

    public static void gatherCustomers() throws SQLException{

        String sql = "SELECT * FROM client_schedule.customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet results = ps.executeQuery();

        while(results.next()){
            Customer currentCustomer = new Customer(results.getInt("Customer_ID"), results.getString("Customer_Name"), results.getString("Address"), results.getInt("Division_ID"), results.getString("Postal_Code"), results.getString("Phone"));
            Customers.addCustomer(currentCustomer);
        }

    }

    public static void gatherCustomerAppointments(int customerId) throws SQLException{
        String sql = "SELECT * FROM client_schedule.appointments WHERE Customer_ID = " + customerId + ";";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet results = ps.executeQuery();

        while(results.next()){
            Appointment currentAppointment = new Appointment(results.getInt("Appointment_ID"), results.getString("Title"), results.getString("Description"), results.getString("Location"), results.getInt("Contact_ID"), results.getString("Type"), results.getString("Start"), results.getString("End"), results.getInt("Customer_ID"), results.getInt("User_ID"));
            ViewCustomerController.currentCustomer.addAppointment(currentAppointment);

        }
    }

    public static int getCustomerId(String name) throws SQLException {
        int id = 0;
        String sql = "SELECT * FROM client_schedule.customers WHERE Customer_Name = '" + name + "';";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet results = ps.executeQuery();

        while(results.next()){
            id = results.getInt("Customer_ID");
        }
        return id;
    }

    public static ArrayList<String> gatherDivisions() throws SQLException {
        ArrayList<String> divisions = new ArrayList<String>();

        String sql = "SELECT * FROM client_schedule.first_level_divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet results = ps.executeQuery();

        while(results.next()){
            divisions.add(results.getString("Division"));
        }

        return divisions;
    }

    public static int searchDivisions(String divisionName, String columnReturned) throws SQLException {
        int id = 0;
        String sql = "SELECT * FROM client_schedule.first_level_divisions WHERE Division = '" + divisionName + "';";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        //ps.setString(0, column);
        //ps.setString(1, divisionName);
        ResultSet results = ps.executeQuery();

        while(results.next()){
            id = results.getInt(columnReturned);
        }
        return id;
    }

    public static String searchDivision(int id, String columnReturned) throws SQLException{
        String divisionName = null;
        String sql = "SELECT * FROM client_schedule.first_level_divisions WHERE Division_ID = " + id;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet results = ps.executeQuery();
        
        while(results.next()){
            divisionName = results.getString(columnReturned);
            
        }
        return divisionName;
    }


    public static String searchCountries(int countryId) throws SQLException {
        String country = null;

        String sql = "SELECT * FROM client_schedule.countries WHERE Country_ID = " + countryId;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet results = ps.executeQuery();

        while(results.next()) {
            country = results.getString("Country");
        }
        return country;
    }

    public static String getContactName(int contactId) throws SQLException {
        String contactName = null;
        String sql = "SELECT * FROM client_schedule.contacts WHERE Contact_ID = " + contactId;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet results = ps.executeQuery();

        while(results.next()) {
            contactName =  results.getString("Contact_Name");
        }
        return contactName;
    }

    public static void insertCustomer(String name, String streetAddress, String zip, String phone, String dateTime, int division) throws SQLException {
        String sql = "INSERT INTO client_schedule.customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES ('" + name + "', '" + streetAddress + "', '" + zip + "', '" + phone + "', '" + dateTime + "', '" + LoginScreen.getCurrentUser() + "', '" + dateTime + "', '" + LoginScreen.getCurrentUser() + "', " + division + ")";
        //System.out.println(sql);
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.execute();
    }

    public static void deleteCustomer(int id) throws SQLException {
        String sql = "DELETE FROM client_schedule.customers WHERE (Customer_ID = " + id + ");";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.execute();
    }

    public static void updateCustomer(int id, String name, String streetAddress, String zip, String phone, String dateTime, int division) throws SQLException {
        String sql = "UPDATE client_schedule.customers SET Customer_Name = '" + name + "', Address = '" + streetAddress + "', Postal_Code = '" + zip + "', Phone = '" + phone + "', Last_Update = '" + dateTime + "', Last_Updated_By = '" + LoginScreen.getCurrentUser() +"', Division_ID = " + division + " WHERE Customer_ID = " + id;
        System.out.println(sql);
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        ps.execute();

    }



}
