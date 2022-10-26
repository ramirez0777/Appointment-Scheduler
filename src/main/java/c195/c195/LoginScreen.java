package c195.c195;


import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**This is the main class and holds the main method. Initiates the Database connection and holds methods to handle time.*/
public class LoginScreen extends Application {
    /**Stage that will be used throughout the program.*/
    public static Stage stage;
    /**Username of the user who is logged in.*/
    private static String currentUser;
    /**User id of the user who is logged in*/
    public static int userId;

    /**Starts the program by displaying the login page*/
    @Override
    public void start(Stage stage) throws IOException {
        LoginScreen.stage = stage;
        changeScreen("login");
        System.out.println("Time zone: " + ZoneId.systemDefault());
        System.out.println(System.getProperty("javafx.runtime.version"));
    }

    /**When main is run it starts a connection to the Database. Connection is closed when program is closed. */
    public static void main(String[] args) {
        JDBC.openConnection();
        launch();
        JDBC.closeConnection();
    }

    /**This method is used to change change screens. Depending on the parameter used it will send you to the appropriate screen and update the title
     * @param newScreen Name of the new page you are going too.
     * */
    public static void changeScreen(String newScreen) throws IOException{
        Stage stage = LoginScreen.stage;
        FXMLLoader fxmlLoader = new FXMLLoader(LoginScreen.class.getResource(newScreen + "-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);

        switch(newScreen){
            case "newcustomer":
                stage.setTitle("Create New Customer");
                break;
            case "reports":
                stage.setTitle("Reports");
                break;
            case "updatecustomer":
                stage.setTitle("Update " + UpdateCustomerController.currentCustomer.getName() + "'s Information");
                break;
            case "viewcustomer":
                stage.setTitle("View " + ViewCustomerController.currentCustomer.getName() + "'s Appointments");
                break;
            case "mainmenu":
                stage.setTitle("Main Menu");
                break;
            case "login":
                stage.setTitle("Login");
                break;
            case "newappointment":
                stage.setTitle("Create New Appointment for " + ViewCustomerController.currentCustomer.getName());
                break;
            case "updateappointment":
                stage.setTitle("Update " + ViewCustomerController.currentCustomer.getName() + "'s appointment");
                break;
            case "allappointments":
                stage.setTitle("All Appointments");
                break;
        }
        stage.setScene(scene);
        stage.show();
    }

    /**Sets the currentUser attribute.
     * @param user Username of current user.
     * */
    public static void setCurrentUser(String user){
        currentUser = user;
    }

    /**Returns the current user.
     * @return Returns current users username.
     * */
    public static String getCurrentUser(){
        return currentUser;
    }

    /**Sets the current users id
     * @param id Current users user id.
     * */
    public static void setUserId(int id) {
        userId = id;
    }

    /**Returns the current user id
     * @return Returns the current users user id.
     * */
    public static int getUserId(){
        return userId;
    }

    /**Returns the current time and date as a string in converted to UTC.
     * @return Returns the current date and time in UTC
     * */
    public static String getCurrentTimeUTC(){
        ZonedDateTime myDateObj = ZonedDateTime.now(ZoneId.of("UTC"));
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return myDateObj.format(myFormatObj);
    }

    /**Converts a LocalDateTime object to UTC and returns it as a string.
     * @param timeToConvert Date and time that will be converted.
     * @return Returns converted date and time.
     * */
    public static String convertTimeToUTC(LocalDateTime timeToConvert){
        ZonedDateTime convertedTime = ZonedDateTime.of(timeToConvert, ZoneId.systemDefault());
        convertedTime = ZonedDateTime.ofInstant(convertedTime.toInstant(), ZoneId.of("UTC"));
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return convertedTime.format(myFormatObj);
    }

    /**Converts a ZonedDateTime object to UTC time
     * @param timeToConvert Date and time to be converted
     * @return Returns time converted to UTC
     * */
    public static ZonedDateTime convertTimeToUTCZDT(ZonedDateTime timeToConvert){
        timeToConvert = ZonedDateTime.ofInstant(timeToConvert.toInstant(), ZoneId.of("UTC"));
        return timeToConvert;
    }

    /**Converts a datetime String in this format: "yyyy-MM-dd HH:mm:ss" in UTC time to local time of PC as string
     * @param dateTime Date and Time in this format: "yyyy-MM-dd HH:mm:ss"
     * @return Returns Date and Time in users current Time zone.
     * */
    public static String convertTimeToLocal(String dateTime){
        //Making datetime received into a parsable String that we then parse into LocalDateTime object
        String parsableDate = dateTime.replace(" ", "T");
        LocalDateTime ldt = LocalDateTime.parse(parsableDate);

        //Making the ldt into a ZonedDateTimeObject that is in the UTC timezone
        ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.of("UTC"));

        //Converting the zdt to the computers timezone which adjusts the time and date
        zdt = ZonedDateTime.ofInstant(zdt.toInstant(), ZoneId.systemDefault());

        //formatting
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        //Return formatted and converted time and date
        return zdt.format(myFormatObj);
    }

    /**Converts time date string in this format: "yyyy-MM-dd HH:mm:ss" to EST time as a string.
     * @param dateTime Time and date in this format: "yyyy-MM-dd HH:mm:ss".
     * @return Returns Time and date converted to America/New_York time zone.
     * */
    public static String convertTimetoEST(String dateTime){
        String parsabledate = dateTime.replace(" ", "T");
        LocalDateTime ldt = LocalDateTime.parse(parsabledate);

        ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.of("UTC"));

        zdt = ZonedDateTime.ofInstant(zdt.toInstant(), ZoneId.of("America/New_York"));

        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        System.out.println(zdt.format(myFormatObj));
        return zdt.format(myFormatObj);
    }

    /**Overloaded method. Converts time date strings in this format: "yyyy-MM-dd" "HH:mm:ss" to a LocalDateTime object
     * @param date Date in this format: "yyyy-MM-dd".
     * @param time Time in this format: "HH:mm:ss".
     * @return Returns date and Time together.
     * */
    public static LocalDateTime convertToLDT(String date, String time){
        String parsabledate = date + "T" + time;
        LocalDateTime ldt = LocalDateTime.parse(parsabledate);
        return ldt;
    }

    /**Overloaded method. Converts time date string in this format: "yyyy-MM-dd HH:mm:ss" to a LocalDateTime object
     * @param dateTime Date and time in this format: "yyyy-MM-dd HH:mm:ss".
     * @return Returns date and time as a LocalDateTime object.
     * */
    public static LocalDateTime convertToLDT(String dateTime){
        String parsabledate = dateTime.replace(" ", "T");
        LocalDateTime ldt = LocalDateTime.parse(parsabledate);
        return ldt;
    }

    /**This method checks if the 2 times given are between the open times of the company in EST, are on a weekend, if end time is before start time, and if the time between start and end spans over 24 hours. If either of these conditions are met it returns false to signify times are invalid
     * @param start Start Time.
     * @param end End time.
     * @return Returns true if times are valid if not return false.
     * */
    public static boolean isCompanyOpen(LocalDateTime start, LocalDateTime end){
        boolean open = true;
        ZonedDateTime startZDT = ZonedDateTime.of(start, ZoneId.of("America/New_York"));
        ZonedDateTime endZDT = ZonedDateTime.of(end, ZoneId.of("America/New_York"));

        start = startZDT.toLocalDateTime();
        end = endZDT.toLocalDateTime();

        LocalTime openTime = LocalTime.of(8, 0);
        LocalTime closeTime = LocalTime.of(22, 0);



        if(start.toLocalTime().isBefore(openTime) || end.toLocalTime().isAfter(closeTime) || endZDT.isBefore(startZDT) || startZDT.isAfter(endZDT) || startZDT.isBefore(ZonedDateTime.now(ZoneId.of("America/New_York")))){
            open = false;
        }

        if(endZDT.getDayOfMonth() - startZDT.getDayOfMonth() > 1){
            open = false;
            System.out.println("Longer than a day appoinitment");
        }

        if(startZDT.getDayOfWeek() == DayOfWeek.SATURDAY || startZDT.getDayOfWeek() == DayOfWeek.SUNDAY || endZDT.getDayOfWeek() == DayOfWeek.SATURDAY || endZDT.getDayOfWeek() == DayOfWeek.SUNDAY){
            open = false;
        }

        return open;
    }
}