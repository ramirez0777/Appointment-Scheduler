package c195.c195;

import helper.Queries;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import java.util.ResourceBundle;


/**Controller for the new customer page. Inserts customers into the database. Has error checking if any fields are left empty when trying to create customer.*/
public class NewCustomerController implements Initializable {

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

    /**When screen is started it fills in the divisions choicebox with possible divisions*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            divisions.getItems().addAll(Queries.gatherDivisions());
        } catch (SQLException e) {}

    }

    /**This method is ran when a division is chosen by the user. It compares the division id with the country to fill in the Country label on the form.*/
    public void setCountry(Event event) throws SQLException {
        int countryId = Queries.searchDivisions((String) divisions.getValue(), "Country_ID");
        currentDivision = Queries.searchDivisions((String) divisions.getValue(), "Division_ID");
        String countryName = Queries.searchCountries(countryId);
        countryLabel.setText(countryName);
    }

    /**This method is used when click the save button. Verifies that all fields are filled in if not shows an error. If they are it adds the customer to the database.*/
    public void saveCustomer() throws IOException, SQLException {
        Alert emptyAlert = new Alert(Alert.AlertType.ERROR, "You left a field(s) empty");
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


        LoginScreen.changeScreen("mainmenu");
    }

    /**Changes screen back to main menu*/
    public void toMainMenu() throws IOException {
        LoginScreen.changeScreen("mainmenu");
    }

}
