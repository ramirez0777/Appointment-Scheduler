package c195.c195;

import helper.queryUsers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Label locationLabel;

    public Label errorLabel;
    public TextField username;
    public TextField password;
    public Button login;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        locationLabel.setText(Locale.getDefault().getCountry());
    };

    public void login() throws SQLException {
        if(queryUsers.verifyLogin(username.getText(), password.getText())){
            errorLabel.setText("Login Verified");
        }
        else{
            errorLabel.setText("Incorrect Username or Password");
        }
    }

}