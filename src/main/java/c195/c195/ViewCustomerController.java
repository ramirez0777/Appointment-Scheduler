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

public class ViewCustomerController implements Initializable {
    public static Customer currentCustomer;
    public Label customerName;
    public TableView appointmentsTable;
    public TableColumn titleColumn;
    public TableColumn descriptionColumn;
    public TableColumn locationColumn;
    public TableColumn contactColumn;
    public TableColumn typeColumn;
    public TableColumn startColumn;
    public TableColumn endColumn;

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
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Appointment has been deleted.");
        alert.showAndWait();
        LoginScreen.changeScreen("viewcustomer");
    }

    public void addAppointment() throws SQLException, IOException {
        LoginScreen.changeScreen("newappointment");
        //Queries.insertAppointment("Temp Title", "Temp Description", "Temp Location", "Temp Type", "2020-05-28 13:00:00", "2020-05-28 14:00:00", 1);
    }
    public void toMainMenu() throws IOException {
        LoginScreen.changeScreen("mainmenu");
    }
}
