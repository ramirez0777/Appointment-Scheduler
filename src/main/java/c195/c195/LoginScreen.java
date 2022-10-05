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

    @Override
    public void start(Stage stage) throws IOException {
        LoginScreen.stage = stage;
        changeScreen("mainmenu");
        System.out.println("Time zone: " + ZoneId.systemDefault());
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

    public static String getCurrentTimeUTC(){
        ZonedDateTime myDateObj = ZonedDateTime.now(ZoneId.of("UTC"));
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return myDateObj.format(myFormatObj);
    }

    public static String convertTimeToLocal(String dateTime){
        //Making datetime received into a parsable String that we then parse into LocalDateTime object
        String dateTimeZoned = dateTime.replace(" ", "T");
        LocalDateTime ldt = LocalDateTime.parse(dateTimeZoned);

        //Making the ldt into a ZonedDateTimeObject that is in the UTC timezone
        ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.of("UTC"));

        //Converting the zdt to the computers timezone which adjusts the time and date
        zdt = ZonedDateTime.ofInstant(zdt.toInstant(), ZoneId.systemDefault());

        //formatting
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        //Return formatted and converted time and date
        return zdt.format(myFormatObj);
    }
}