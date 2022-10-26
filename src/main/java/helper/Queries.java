package helper;

import c195.c195.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;


/**Queries class holds all the queries run against the database throughout the program. This class is referenced in most classes*/
public abstract class Queries {

    /**Verifies that the username and password entered are correct. Return false if they are not.
     * @param username Username inputted.
     * @param password Password inputted.
     * @return True if credentials are correct, false if not.
     * */
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

    /**Returns an ObservableList of all customers in the database.
     * @return Returns list of all customers.
     * */
    public static ObservableList<Customer> gatherCustomers() throws SQLException{
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        String sql = "SELECT * FROM client_schedule.customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet results = ps.executeQuery();

        while(results.next()){
            Customer currentCustomer = new Customer(results.getInt("Customer_ID"), results.getString("Customer_Name"), results.getString("Address"), results.getInt("Division_ID"), results.getString("Postal_Code"), results.getString("Phone"));
            customers.add(currentCustomer);
        }
        return customers;
    }

    /**Returns ObservableList of all appointments in the database.
     * @return Returns list of all appointments.
     * */
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

    /**Adds appointments to the current customer in the view customer screen from the database.
     * @param customerId Customer id to search by.
     * */
    public static void gatherCustomerAppointments(int customerId) throws SQLException{
        String sql = "SELECT * FROM client_schedule.appointments WHERE Customer_ID = " + customerId + ";";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet results = ps.executeQuery();

        while(results.next()){
            Appointment currentAppointment = new Appointment(results.getInt("Appointment_ID"), results.getString("Title"), results.getString("Description"), results.getString("Location"), results.getInt("Contact_ID"), results.getString("Type"), results.getString("Start"), results.getString("End"), results.getInt("Customer_ID"), results.getInt("User_ID"));
            ViewCustomerController.currentCustomer.addAppointment(currentAppointment);

        }
    }

    /**Returns ArrayList of strings that are the contact names in the database.
     * @return Returns ist of contacts.
     * */
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

    /**Returns the customer id by comparing it in the database to the name of the customer.
     * @param name Customer name to search by.
     * @return Returns the customer id.
     * */
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

    /**Returns ArrayList of Strings of all the divisions in the database.
     * @return List of first level divisions.
     * */
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

    /**Returns division id by comparing it with the division name in the database.
     * @param divisionName Name of first level division.
     * @param columnReturned Name of Column to return.
     * @return Returns division id.
     * */
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

    /**Returns division name by comparing it with the division id in the database.
     * @param id Division id.
     * @param columnReturned Column to search by.
     * @return Returns the division name.
     * */
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

    /**Returns country name by comparing it with the country id in the database.
     * @param countryId ID number of the country.
     * @return Returns country name.
     * */
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

    /**Returns contact name by comparing the contact id in the database.
     * @param contactId Contacts id number.
     * @return Returns contacts name.
     * */
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

    /**Returns contact id by comparing the contact name in the database.
     * @param contactName Name of contact.
     * @return Returns contatcs id number.
     * */
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

    /**Returns username by comparing the user id in the database.
     * @param id Users id.
     * @return Returns users username.
     * */
    public static String getUsername(int id) throws SQLException{
        String username = null;
        String sql = "SELECT * FROM client_schedule.users WHERE User_ID = " + id;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet results = ps.executeQuery();

        while(results.next()){
            username = results.getString("User_Name");
        }

        return username;
    }

    /**Inserts new customer into database.
     * @param name Customers name.
     * @param streetAddress Customers street address.
     * @param zip Customers zip code.
     * @param phone Cutomers phone number.
     * @param dateTime Current Time in UTC.
     * @param division First level division id.
     * */
    public static void insertCustomer(String name, String streetAddress, String zip, String phone, String dateTime, int division) throws SQLException {
        String sql = "INSERT INTO client_schedule.customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES ('" + name + "', '" + streetAddress + "', '" + zip + "', '" + phone + "', '" + dateTime + "', '" + LoginScreen.getCurrentUser() + "', '" + dateTime + "', '" + LoginScreen.getCurrentUser() + "', " + division + ")";
        //System.out.println(sql);
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.execute();
    }

    /**Inserts new appointment into database.
     * @param title Title of appointment.
     * @param description Description of appointment.
     * @param location Location of appointment.
     * @param type Type of appointment.
     * @param startTime Time and Date of the start of appointment.
     * @param endTime Time and Date of the end of appointment.
     * @param contactId ID of the contact assigned to the appointment.
     * */
    public static void insertAppointment(String title, String description, String location, String type, String startTime, String endTime, int contactId) throws SQLException{
        String sql = "INSERT INTO client_schedule.appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES ('" + title + "', '" + description + "', '" + location + "', '" + type + "', '" + startTime + "', '" + endTime + "', '" + LoginScreen.getCurrentTimeUTC() + "', '" + LoginScreen.getCurrentUser() + "', '" + LoginScreen.getCurrentTimeUTC() + "', '" + LoginScreen.getCurrentUser() + "', " +  ViewCustomerController.currentCustomer.getCustomerId() + ", " + LoginScreen.getUserId() + ", " + contactId + ");";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.execute();
    }

    /**Updates appointment on database according to the Appointments ID.
     * @param title Title of appointment.
     * @param description Description of appointment.
     * @param location Location of appointment.
     * @param type Type of appointment.
     * @param startTime Time and date of the start of appointment.
     * @param endTime Time and date of the end of appointment.
     * @param contactId ID of the contact assigned to the appointment.
     * */
    public static void updateAppointment(String title, String description, String location, String type, String startTime, String endTime, int contactId) throws SQLException{
        String sql = "UPDATE client_schedule.appointments SET Title = '" + title + "', Description = '" + description + "', Location = '" + location + "', Type = '" + type + "', Start = '" + startTime + "', End = '" + endTime + "', Last_Update = '" + LoginScreen.getCurrentTimeUTC() + "', Last_Updated_By = '" + LoginScreen.getCurrentUser() + "', Contact_ID = " + contactId + " WHERE Appointment_ID = " + UpdateAppointmentController.currentAppointment.getAppointmentId();
        System.out.println(sql);
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.execute();
    }

    /**Deletes customer from database according to it's Customer ID.
     * @param id ID of the customer to be deleted.
     * */
    public static void deleteCustomer(int id) throws SQLException {
        String sql = "DELETE FROM client_schedule.customers WHERE (Customer_ID = " + id + ");";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.execute();
    }

    /**Deletes appointment from database according to it's Appointment ID
     * @param id ID of the appointment to be deleted.
     * */
    public static void deleteAppointment(int id) throws SQLException{
        String sql = "DELETE FROM client_schedule.appointments WHERE (Appointment_ID = " + id + ");";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.execute();
    }

    /**Updates customer in database according to its Customer ID.
     * @param name Name of customer.
     * @param id ID of customer.
     * @param streetAddress Street address of customer.
     * @param zip Zip code of the customer.
     * @param phone Phone number of the customer.
     * @param dateTime Current Time in UTC.
     * @param division First level division id.
     * */
    public static void updateCustomer(int id, String name, String streetAddress, String zip, String phone, String dateTime, int division) throws SQLException {
        String sql = "UPDATE client_schedule.customers SET Customer_Name = '" + name + "', Address = '" + streetAddress + "', Postal_Code = '" + zip + "', Phone = '" + phone + "', Last_Update = '" + dateTime + "', Last_Updated_By = '" + LoginScreen.getCurrentUser() +"', Division_ID = " + division + " WHERE Customer_ID = " + id;
        //System.out.println(sql);
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.execute();
    }

    /**Compares 2 Date Times against all appointments the customer has currently. If they overlap it returns false.
     * @param start Start time and date of appointment.
     * @param end End time and date of appointment
     * @return Returns true if there is an overlap with appointments, false if not.
     * */
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

    /**Overloaded method. Compares 2 Date Times against all appointments the customer has currently except itself. If they overlap it returns false.
     * @param start Start date and time of appointment.
     * @param end End date and time of appointment.
     * @param appointmentId  ID of current appointment.
     * @return Returns true if there is overlapping appointments, false if not.
     * */
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
