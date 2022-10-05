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
import java.util.ResourceBundle;

public class UpdateCustomerController implements Initializable {

    public TextField nameField;
    public TextField streetAddressField;

    public TextField zipField;
    public TextField phoneField;
    public ChoiceBox divisions;
    public Label countryLabel;
    public int currentDivision = 0;
    public static Customer currentCustomer;
    public static int index;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Fill in drop down of divisions
        try {
            divisions.getItems().addAll(Queries.gatherDivisions());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Fill in boxes with current information

        nameField.setText(currentCustomer.getName());
        streetAddressField.setText(currentCustomer.getStreetAddress());
        zipField.setText(currentCustomer.getPostalCode());
        phoneField.setText(currentCustomer.getPhone());
        //division id - 1 is the index
        divisions.getSelectionModel().select(currentCustomer.getDivisionId() - 1);
        try {
            setCountry();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void setCountry() throws SQLException {
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



        //Update on DB
        String currentDateTime = LoginScreen.getCurrentTimeUTC();
        Queries.updateCustomer(currentCustomer.getCustomerId(), name, streetAddress, zip, phone, currentDateTime, currentDivision);


        System.out.println("Customer saved");
        LoginScreen.changeScreen("mainmenu");
    }


    public void toMainMenu() throws IOException {
        LoginScreen.changeScreen("mainmenu");
    }
}
