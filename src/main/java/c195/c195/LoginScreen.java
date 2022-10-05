package c195.c195;


import helper.JDBC;
import helper.TestQueries;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LoginScreen extends Application {
    public static Stage stage;
    private static String currentUser;

    @Override
    public void start(Stage stage) throws IOException {
        LoginScreen.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(LoginScreen.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Welcome!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        JDBC.openConnection();
        launch();
        JDBC.closeConnection();
    }

    public static void changeScreen(String newScreen) throws IOException{
        Stage stage = LoginScreen.stage;
        FXMLLoader fxmlLoader = new FXMLLoader(LoginScreen.class.getResource(newScreen + "-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

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
            default:
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
        String currentDateTime = myDateObj.format(myFormatObj);
        return currentDateTime;
    }
}