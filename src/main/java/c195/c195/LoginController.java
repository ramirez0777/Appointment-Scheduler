package c195.c195;

import helper.Queries;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
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

    public Label errorLabel;
    public TextField username;
    public TextField password;
    public Button login;

    public String language;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        language = Locale.getDefault().getLanguage();
        //System.out.println("Default: " + Locale.getDefault().getLanguage());
        locationLabel.setText(Locale.getDefault().getCountry());

        if(language.equals("fr")){

        }
        else{

        }
    };

    public void login() throws SQLException, IOException {
        if(Queries.verifyLogin(username.getText(), password.getText())){
            Stage stage = LoginScreen.stage;
            FXMLLoader fxmlLoader = new FXMLLoader(LoginScreen.class.getResource("mainmenu-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle("Main Menu!");
            stage.setScene(scene);
            stage.show();

        }
        else{
            errorLabel.setText("Incorrect Username or Password");
        }
    }

}