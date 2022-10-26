package c195.c195;

import helper.Queries;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**Controller for update customer screen. Fills in information from customer and allows user to update information.*/
public class UpdateCustomerController implements Initializable {

    /**Text field for user to type in name of customer.*/
    public TextField nameField;
    /**Text field for user to type in street address of customer.*/
    public TextField streetAddressField;
    /**Text field for user to type in zip code of customer.*/
    public TextField zipField;
    /**Text field for user to type in phone number of customer.*/
    public TextField phoneField;
    /**Selection box for user to choose first level division of customer.*/
    public ChoiceBox divisions;
    /**Label that is updated according to division.*/
    public Label countryLabel;
    /**ID of current first level division selected, initiates at 0.*/
    public int currentDivision = 0;
    /**Current customer being edited.*/
    public static Customer currentCustomer;


    /**Fills in information from the customer onto the form.*/
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

    /**Runs when a division is selected. Sets country label depending on what division is selected*/
    public void setCountry() throws SQLException {
        int countryId = Queries.searchDivisions((String) divisions.getValue(), "Country_ID");
        currentDivision = Queries.searchDivisions((String) divisions.getValue(), "Division_ID");
        String countryName = Queries.searchCountries(countryId);
        countryLabel.setText(countryName);
    }

    /**Verifies all fields are filled in if so it updates the customer in the database. If not it shows an error*/
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

    /**Changes screen back to main menu screen*/
    public void toMainMenu() throws IOException {
        LoginScreen.changeScreen("mainmenu");
    }
}
