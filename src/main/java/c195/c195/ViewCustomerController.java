package c195.c195;

import helper.Queries;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**Controller for view customer screen. Fills in table with appointments from that customer*/
public class ViewCustomerController implements Initializable {
    /**Current customer being viewed.*/
    public static Customer currentCustomer;
    /**Label for the customers name.*/
    public Label customerName;
    /**Table to display customers appointments.*/
    public TableView appointmentsTable;
    /**Column to display title of appointments.*/
    public TableColumn titleColumn;
    /**Column to display description of appointments.*/
    public TableColumn descriptionColumn;
    /**Column to display location of appointments.*/
    public TableColumn locationColumn;
    /**Column to display contact of appointments.*/
    public TableColumn contactColumn;
    /**Column to display type of appointments.*/
    public TableColumn typeColumn;
    /**Column to display start time of appointments.*/
    public TableColumn startColumn;
    /**Column to display end time of appointments.*/
    public TableColumn endColumn;

    /**Fills in table with customer appointments. Sets name in Labels to clearly show which customer is being viewed. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            currentCustomer.clearAppointments();
            Queries.gatherCustomerAppointments(currentCustomer.getCustomerId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        customerName.setText(currentCustomer.getName());

        //fill in table
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));

        appointmentsTable.setItems(currentCustomer.getAppointments());
    }

    /**Allows user to delete 1 appointment selected. If appointment isn't selected it shows an error. Confirms with user that appointment will be deleted, then shows user that the apointment was deleted along with the type and appointment id*/
    public void deleteAppointment() throws SQLException, IOException {
        Appointment selectedAppointment = (Appointment) appointmentsTable.getSelectionModel().getSelectedItem();

        if(selectedAppointment == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select an appointment to delete");
            alert.showAndWait();
            return;
        }

        Alert confirmDeletion = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer?");
        Optional<ButtonType> confirm = confirmDeletion.showAndWait();
        if(!(confirm.isPresent() && confirm.get() == ButtonType.OK)){
            return;
        }

        Queries.deleteAppointment(selectedAppointment.getAppointmentId());
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Appointment has been deleted. Type: " + selectedAppointment.getType() + ". Appointment ID: " + selectedAppointment.getAppointmentId());
        alert.showAndWait();
        LoginScreen.changeScreen("viewcustomer");
    }

    /**Changes screen to the new appointment screen*/
    public void addAppointment() throws SQLException, IOException {
        LoginScreen.changeScreen("newappointment");
    }

    /**Changes screen to the main menu screen*/
    public void toMainMenu() throws IOException {
        LoginScreen.changeScreen("mainmenu");
    }

    /**Changes Screen to update appointment screen and passes through appointment to that screen. If appointment is not selected it shows an error.*/
    public void updateAppointment() throws IOException{
        if(appointmentsTable.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select an appointment to update");
            alert.showAndWait();
            return;
        }

        UpdateAppointmentController.currentAppointment = (Appointment) appointmentsTable.getSelectionModel().getSelectedItem();
        LoginScreen.changeScreen("updateappointment");
    }
}
