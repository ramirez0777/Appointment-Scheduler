package c195.c195;

import helper.Queries;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Label locationLabel;
    public Label usernameLabel;
    public Label passwordLabel;
    public Label location;
    public TextField username;
    public TextField password;
    public Button login;
    private ResourceBundle rb;
    public String language;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        rb = ResourceBundle.getBundle("region", Locale.getDefault());
        System.out.println(rb.getString("error"));
        language = Locale.getDefault().getLanguage();
        //System.out.println("Default: " + Locale.getDefault().getLanguage());
        location.setText(Locale.getDefault().getCountry());

        if(language.equals("fr")){
            locationLabel.setText(rb.getString("location"));
            usernameLabel.setText(rb.getString("user"));
            passwordLabel.setText(rb.getString("pass"));
            login.setText(rb.getString("login"));
            LoginScreen.stage.setTitle("login");
        }

    };

    public void login() throws SQLException, IOException {
        if(Queries.verifyLogin(username.getText(), password.getText())){
            LoginScreen.changeScreen("allappointments");
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect Username or Password");
            if(language.equals("fr")){
                alert.setContentText(rb.getString("loginerror"));
                alert.setHeaderText(rb.getString("error"));
            }
            alert.showAndWait();
        }
    }

}