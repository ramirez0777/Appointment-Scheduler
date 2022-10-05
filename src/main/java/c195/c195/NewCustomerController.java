package c195.c195;

import helper.Queries;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.Customers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class NewCustomerController implements Initializable {

    public TextField nameField;
    public TextField streetAddressField;

    public TextField zipField;
    public TextField phoneField;
    public ChoiceBox divisions;
    public Label countryLabel;
    public int currentDivision = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Query customers table for the max customer id. Add 1 to it and set that as the customer Id for the new one.

        //Fill in drop down of divisions
        try {
            divisions.getItems().addAll(Queries.gatherDivisions());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

    public void setCountry(Event event) throws SQLException {
        int countryId = Queries.searchDivisions((String) divisions.getValue(), "Country_ID");
        currentDivision = Queries.searchDivisions((String) divisions.getValue(), "Division_ID");
        String countryName = Queries.searchCountries(countryId);
        countryLabel.setText(countryName);
    }

    public void saveCustomer() throws IOException, SQLException {
        Alert emptyAlert = new Alert(Alert.AlertType.ERROR, "You left a field(s) empty");
        int newId;
        String name;
        String streetAddress;
        String zip;
        String phone;

        if (nameField.getText().equals("") || streetAddressField.getText().equals("") || zipField.getText().equals("") || phoneField.getText().equals("") || currentDivision == 0) {
            emptyAlert.showAndWait();
            return;
        } else{
            name = nameField.getText();
            streetAddress = streetAddressField.getText();
            zip = zipField.getText();
            phone = phoneField.getText();
        }

        //Add to DB
        String currentDateTime = LoginScreen.getCurrentTimeUTC();
        Queries.insertCustomer(name, streetAddress, zip, phone, currentDateTime, currentDivision);


        System.out.println("Customer saved");
        LoginScreen.changeScreen("mainmenu");
    }

    public void toMainMenu() throws IOException {
        LoginScreen.changeScreen("mainmenu");
    }

}
