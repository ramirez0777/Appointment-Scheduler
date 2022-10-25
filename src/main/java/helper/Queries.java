package helper;

import c195.c195.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Customers;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
                    LoginScreen.setUserId(results.getInt("User_ID"));
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

    public static ObservableList<Appointment> gatherAppointments() throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM client_schedule.appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet results = ps.executeQuery();

        while(results.next()){
            Appointment currentAppointment = new Appointment(results.getInt("Appointment_ID"), results.getString("Title"), results.getString("Description"), results.getString("Location"), results.getInt("Contact_ID"), results.getString("Type"), results.getString("Start"), results.getString("End"), results.getInt("Customer_ID"), results.getInt("User_ID"));
            appointments.add(currentAppointment);
        }
        return appointments;
    }

    public static ObservableList<Appointment> gatherContactAppointments(int contactId) throws SQLException{
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM client_schedule.appointments WHERE Contact_ID = " + contactId;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet results = ps.executeQuery();

        while(results.next()){
            Appointment currentAppointment = new Appointment(results.getInt("Appointment_ID"), results.getString("Title"), results.getString("Description"), results.getString("Location"), results.getInt("Contact_ID"), results.getString("Type"), results.getString("Start"), results.getString("End"), results.getInt("Customer_ID"), results.getInt("User_ID"));
            appointments.add(currentAppointment);
        }
        return appointments;
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

    public static ArrayList<String> gatherContacts() throws SQLException {
        ArrayList<String> contacts = new ArrayList<String>();
        String sql = "SELECT * FROM client_schedule.contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet results = ps.executeQuery();

        while(results.next()){
            contacts.add(results.getString("Contact_Name"));
        }
        return contacts;
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

    public static int getContactId(String contactName) throws SQLException{
        int contactId = 0;
        String sql = "SELECT * FROM client_schedule.contacts WHERE Contact_Name = '" + contactName + "'";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet results = ps.executeQuery();

        while(results.next()){
            contactId = results.getInt("Contact_ID");
        }

        return contactId;
    }

    public static void insertCustomer(String name, String streetAddress, String zip, String phone, String dateTime, int division) throws SQLException {
        String sql = "INSERT INTO client_schedule.customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES ('" + name + "', '" + streetAddress + "', '" + zip + "', '" + phone + "', '" + dateTime + "', '" + LoginScreen.getCurrentUser() + "', '" + dateTime + "', '" + LoginScreen.getCurrentUser() + "', " + division + ")";
        //System.out.println(sql);
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.execute();
    }

    public static void insertAppointment(String title, String description, String location, String type, String startTime, String endTime, int contactId) throws SQLException{
        String sql = "INSERT INTO client_schedule.appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES ('" + title + "', '" + description + "', '" + location + "', '" + type + "', '" + startTime + "', '" + endTime + "', '" + LoginScreen.getCurrentTimeUTC() + "', '" + LoginScreen.getCurrentUser() + "', '" + LoginScreen.getCurrentTimeUTC() + "', '" + LoginScreen.getCurrentUser() + "', " +  ViewCustomerController.currentCustomer.getCustomerId() + ", " + LoginScreen.getUserId() + ", " + contactId + ");";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.execute();
    }

    public static void updateAppointment(String title, String description, String location, String type, String startTime, String endTime, int contactId) throws SQLException{
        String sql = "UPDATE client_schedule.appointments SET Title = '" + title + "', Description = '" + description + "', Location = '" + location + "', Type = '" + type + "', Start = '" + startTime + "', End = '" + endTime + "', Last_Update = '" + LoginScreen.getCurrentTimeUTC() + "', Last_Updated_By = '" + LoginScreen.getCurrentUser() + "', Contact_ID = " + contactId + " WHERE Appointment_ID = " + UpdateAppointmentController.currentAppointment.getAppointmentId();
        System.out.println(sql);
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.execute();
    }

    public static void deleteCustomer(int id) throws SQLException {
        String sql = "DELETE FROM client_schedule.customers WHERE (Customer_ID = " + id + ");";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.execute();
    }

    public static void deleteAppointment(int id) throws SQLException{
        String sql = "DELETE FROM client_schedule.appointments WHERE (Appointment_ID = " + id + ");";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.execute();
    }

    public static void updateCustomer(int id, String name, String streetAddress, String zip, String phone, String dateTime, int division) throws SQLException {
        String sql = "UPDATE client_schedule.customers SET Customer_Name = '" + name + "', Address = '" + streetAddress + "', Postal_Code = '" + zip + "', Phone = '" + phone + "', Last_Update = '" + dateTime + "', Last_Updated_By = '" + LoginScreen.getCurrentUser() +"', Division_ID = " + division + " WHERE Customer_ID = " + id;
        //System.out.println(sql);
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        ps.execute();

    }

    public static boolean overlappingAppointments(LocalDateTime start, LocalDateTime end) throws SQLException {
        boolean isOverlapping = false;

        ZonedDateTime startZDT = ZonedDateTime.of(start, ZoneId.systemDefault());
        ZonedDateTime endZDT = ZonedDateTime.of(end, ZoneId.systemDefault());

        startZDT = LoginScreen.convertTimeToUTCZDT(startZDT);
        endZDT = LoginScreen.convertTimeToUTCZDT(endZDT);


        String sql = "SELECT * FROM client_schedule.appointments WHERE Customer_ID = " + ViewCustomerController.currentCustomer.getCustomerId();
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet results = ps.executeQuery();

        while(results.next()){
            String parsableStart = results.getString("Start").replace(" ", "T");
            String parsableEnd = results.getString("End").replace(" ", "T");
            ZonedDateTime dbStart = ZonedDateTime.of(LocalDateTime.parse(parsableStart), ZoneId.of("UTC"));
            ZonedDateTime dbEnd = ZonedDateTime.of(LocalDateTime.parse(parsableEnd), ZoneId.of("UTC"));

            if( (startZDT.isAfter(dbStart) && startZDT.isBefore(dbEnd)) || (endZDT.isBefore(dbEnd) && endZDT.isAfter(dbStart))){
                isOverlapping = true;
            }
            if( (dbStart.isAfter(startZDT) && dbStart.isBefore(endZDT)) || (dbEnd.isBefore(endZDT) && dbEnd.isAfter(startZDT)) ){
                isOverlapping = true;
            }
            if(startZDT.isEqual(dbStart) || endZDT.isEqual(dbEnd)){
                isOverlapping = true;
            }
        }

        return isOverlapping;
    }

    public static boolean overlappingAppointments(LocalDateTime start, LocalDateTime end, int appointmentId) throws SQLException {
        boolean isOverlapping = false;

        ZonedDateTime startZDT = ZonedDateTime.of(start, ZoneId.systemDefault());
        ZonedDateTime endZDT = ZonedDateTime.of(end, ZoneId.systemDefault());

        startZDT = LoginScreen.convertTimeToUTCZDT(startZDT);
        endZDT = LoginScreen.convertTimeToUTCZDT(endZDT);


        String sql = "SELECT * FROM client_schedule.appointments WHERE Customer_ID = " + ViewCustomerController.currentCustomer.getCustomerId() + " AND Appointment_ID != " + appointmentId;

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet results = ps.executeQuery();

        while(results.next()){
            String parsableStart = results.getString("Start").replace(" ", "T");
            String parsableEnd = results.getString("End").replace(" ", "T");
            ZonedDateTime dbStart = ZonedDateTime.of(LocalDateTime.parse(parsableStart), ZoneId.of("UTC"));
            ZonedDateTime dbEnd = ZonedDateTime.of(LocalDateTime.parse(parsableEnd), ZoneId.of("UTC"));

            if( (startZDT.isAfter(dbStart) && startZDT.isBefore(dbEnd)) || (endZDT.isBefore(dbEnd) && endZDT.isAfter(dbStart))){
                isOverlapping = true;
            }
            if( (dbStart.isAfter(startZDT) && dbStart.isBefore(endZDT)) || (dbEnd.isBefore(endZDT) && dbEnd.isAfter(startZDT)) ){
                isOverlapping = true;
            }
            if(startZDT.isEqual(dbStart) || endZDT.isEqual(dbEnd)){
                isOverlapping = true;
            }
        }

        return isOverlapping;
    }

}
