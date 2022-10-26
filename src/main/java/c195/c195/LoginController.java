package c195.c195;

import helper.Queries;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**This class is the controller for the login page. This page displays a login form, the users locale, and is translated to French if needed. Also shows errors for logins and updates the login activity file.*/
public class LoginController implements Initializable {
    /**"Login Location" label.*/
    public Label locationLabel;
    /**"Username" label.*/
    public Label usernameLabel;
    /**"Password label.*/
    public Label passwordLabel;
    /**Label for location*/
    public Label location;
    /**Textfield for user to type in username*/
    public TextField username;
    /**Textfield for user to type in password*/
    public TextField password;
    /**Login button*/
    public Button login;
    /**Resource bundle to translate labels and errors*/
    private ResourceBundle rb;
    /**Default Language of users PC*/
    public String language;

    /**The class implements Intializable. When initialized it detects your region and translates the login page to French if needed.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        rb = ResourceBundle.getBundle("region", Locale.getDefault());
        language = Locale.getDefault().getLanguage();
        //System.out.println("Default: " + Locale.getDefault().getLanguage());
        location.setText(String.valueOf(ZoneId.systemDefault()));

        if(language.equals("fr")){
            locationLabel.setText(rb.getString("location"));
            usernameLabel.setText(rb.getString("user"));
            passwordLabel.setText(rb.getString("pass"));
            login.setText(rb.getString("login"));
            LoginScreen.stage.setTitle("login");
        }

    };

    /**When the Login button is pressed it checks with the database if your information is correct. It logs the username, current time in UTC, and if was successful or unsucessful. If it was succesful it sends yo to the all appointments page. If not it shows an error.*/
    public void login() throws SQLException, IOException {
        FileWriter fw = new FileWriter("src/login_activity.txt", true);
        PrintWriter updateLoginAttempts = new PrintWriter(fw);
        if(Queries.verifyLogin(username.getText(), password.getText())){
            updateLoginAttempts.println(username.getText() + ", " + LoginScreen.getCurrentTimeUTC() + ", Successful");
            updateLoginAttempts.close();
            LoginScreen.changeScreen("allappointments");
        }
        else{
            updateLoginAttempts.println(username.getText() + ", " + LoginScreen.getCurrentTimeUTC() + ", Unsuccessful");
            updateLoginAttempts.close();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect Username or Password");
            if(language.equals("fr")){
                alert.setContentText(rb.getString("loginerror"));
                alert.setHeaderText(rb.getString("error"));
            }
            alert.showAndWait();
        }
    }

}