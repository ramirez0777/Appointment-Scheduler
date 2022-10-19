package c195.c195;


import helper.JDBC;
import helper.TestQueries;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateTimeStringConverter;

import java.io.IOException;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class LoginScreen extends Application {
    public static Stage stage;
    private static String currentUser;
    public static int userId;

    @Override
    public void start(Stage stage) throws IOException {
        LoginScreen.stage = stage;
        changeScreen("login");
        System.out.println("Time zone: " + ZoneId.systemDefault());
        convertTimetoEST(getCurrentTimeUTC());
    }

    public static void main(String[] args) {
        JDBC.openConnection();
        launch();
        JDBC.closeConnection();
    }

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
        }
        stage.setScene(scene);
        stage.show();
    }

    public static void setCurrentUser(String user){
        currentUser = user;
    }

    public static String getCurrentUser(){
        return currentUser;
    }

    public static void setUserId(int id) {
        userId = id;
    }
    public static int getUserId(){
        return userId;
    }

    public static String getCurrentTimeUTC(){
        ZonedDateTime myDateObj = ZonedDateTime.now(ZoneId.of("UTC"));
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return myDateObj.format(myFormatObj);
    }

    public static String convertTimeToUTC(LocalDateTime timeToConvert){
        ZonedDateTime convertedTime = ZonedDateTime.of(timeToConvert, ZoneId.systemDefault());
        convertedTime = ZonedDateTime.ofInstant(convertedTime.toInstant(), ZoneId.of("UTC"));
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return convertedTime.format(myFormatObj);
    }

    public static ZonedDateTime convertTimeToUTCZDT(ZonedDateTime timeToConvert){
        timeToConvert = ZonedDateTime.ofInstant(timeToConvert.toInstant(), ZoneId.of("UTC"));
        return timeToConvert;
    }

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

    public static String convertTimetoEST(String dateTime){
        String parsabledate = dateTime.replace(" ", "T");
        LocalDateTime ldt = LocalDateTime.parse(parsabledate);

        ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.of("UTC"));

        zdt = ZonedDateTime.ofInstant(zdt.toInstant(), ZoneId.of("America/New_York"));

        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        System.out.println(zdt.format(myFormatObj));
        return zdt.format(myFormatObj);
    }

    public static LocalDateTime convertToLDT(String date, String time){
        String parsabledate = date + "T" + time;
        LocalDateTime ldt = LocalDateTime.parse(parsabledate);
        return ldt;
    }

    public static LocalDateTime convertoLDT(String dateTime){
        String parsabledate = dateTime.replace(" ", "T");
        LocalDateTime ldt = LocalDateTime.parse(parsabledate);
        return ldt;
    }

    public static boolean isCompanyOpen(LocalDateTime start, LocalDateTime end){
        boolean open = true;
        ZonedDateTime startZDT = ZonedDateTime.of(start, ZoneId.of("America/New_York"));
        ZonedDateTime endZDT = ZonedDateTime.of(end, ZoneId.of("America/New_York"));

        start = startZDT.toLocalDateTime();
        end = endZDT.toLocalDateTime();

        LocalTime openTime = LocalTime.of(8, 0);
        LocalTime closeTime = LocalTime.of(22, 0);



        if(start.toLocalTime().isBefore(openTime) || end.toLocalTime().isAfter(closeTime) || endZDT.isBefore(startZDT) || startZDT.isAfter(endZDT) || startZDT.isBefore(ZonedDateTime.now(ZoneId.of("America/New_York"))) || endZDT.getDayOfMonth() - startZDT.getDayOfMonth() > 1){
            open = false;
        }

        if(startZDT.getDayOfWeek() == DayOfWeek.SATURDAY || startZDT.getDayOfWeek() == DayOfWeek.SUNDAY || endZDT.getDayOfWeek() == DayOfWeek.SATURDAY || endZDT.getDayOfWeek() == DayOfWeek.SUNDAY){
            open = false;
        }


        return open;
    }
}