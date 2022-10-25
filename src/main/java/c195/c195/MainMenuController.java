package c195.c195;

import helper.Queries;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.Customers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    public TableView customersTable;
    public TableColumn customers;
    public TableColumn location;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Customers.clearCustomers();
            Queries.gatherCustomers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Customer brian = new Customer(1, "brian", "street", 1, "zip", "801");
        customers.setCellValueFactory(new PropertyValueFactory<>("name"));
        location.setCellValueFactory(new PropertyValueFactory<>("division"));
        customersTable.setItems(Customers.getAllCustomers());
    }

    public void deleteCustomer() throws SQLException, IOException {
        Customer selectedCustomer = (Customer) customersTable.getSelectionModel().getSelectedItem();

        Alert confirmDeletion = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer? This will also delete all their appointments");
        Optional<ButtonType> confirm = confirmDeletion.showAndWait();
        if(!(confirm.isPresent() && confirm.get() == ButtonType.OK)){
            return;
        }

        for(Appointment currentAppointment : selectedCustomer.getAppointments()){
            Queries.deleteAppointment(currentAppointment.getAppointmentId());
        }

        Queries.deleteCustomer(selectedCustomer.getCustomerId());

        Alert alert = new Alert(Alert.AlertType.INFORMATION, selectedCustomer.getName() + " has been deleted");
        alert.showAndWait();
        LoginScreen.changeScreen("mainmenu");
    }

    public void toNewCustomer() throws IOException {
        LoginScreen.changeScreen("newcustomer");
    }

    public void toUpdateCustomer(ActionEvent actionEvent) throws IOException {
        Customer selectedCustomer = (Customer) customersTable.getSelectionModel().getSelectedItem();
        if(selectedCustomer == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a customer to update");
            alert.showAndWait();
        }
        else {
            UpdateCustomerController.index = Customers.getAllCustomers().indexOf(selectedCustomer);
            UpdateCustomerController.currentCustomer = selectedCustomer;
            LoginScreen.changeScreen("updatecustomer");
        }
    }
    public void toViewCustomer(ActionEvent actionEvent) throws IOException {
        Customer selectedCustomer = (Customer) customersTable.getSelectionModel().getSelectedItem();

        if(selectedCustomer == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a customer to view");
            alert.showAndWait();
        }
        else{
            ViewCustomerController.currentCustomer = selectedCustomer;
            LoginScreen.changeScreen("viewcustomer");
        }
    }
    public void toReports(ActionEvent actionEvent) throws IOException {
        LoginScreen.changeScreen("reports");
    }

    public void toAllAppointments() throws IOException{
        LoginScreen.changeScreen("allappointments");
    }
}
