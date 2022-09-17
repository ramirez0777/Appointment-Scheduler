package c195.c195;

import helper.JDBC;
import helper.TestQueries;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;

public class LoginScreen extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginScreen.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        JDBC.openConnection();
        System.out.println("Username is: " + TestQueries.searchUsers(3));
        JDBC.closeConnection();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}