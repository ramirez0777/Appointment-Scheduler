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

    private static boolean firstTime = true;

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

    public void deleteCustomer() throws SQLException {
        Customer selectedCustomer = (Customer) customersTable.getSelectionModel().getSelectedItem();

        Alert confirmDeletion = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer?");
        Optional<ButtonType> confirm = confirmDeletion.showAndWait();
        if(!(confirm.isPresent() && confirm.get() == ButtonType.OK)){
            return;
        }

        if(Customers.deleteCustomer(selectedCustomer)){
            Queries.deleteCustomer(selectedCustomer.getCustomerId());
            Alert alert = new Alert(Alert.AlertType.INFORMATION, selectedCustomer.getName() + " has been deleted");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Customer was not deleted (Customers with appointments cannot be deleted)");
            alert.showAndWait();
        }
    }

    public void toNewCustomer() throws IOException {
        LoginScreen.changeScreen("newcustomer");
    }

    public void toUpdateCustomer(ActionEvent actionEvent) throws IOException {
        Customer selectedCustomer = (Customer) customersTable.getSelectionModel().getSelectedItem();
        if(selectedCustomer == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a customer to update");
        }
        else {
            UpdateCustomerController.index = Customers.getAllCustomers().indexOf(selectedCustomer);
            UpdateCustomerController.currentCustomer = selectedCustomer;
            LoginScreen.changeScreen("updatecustomer");
        }
    }
    public void toViewCustomer(ActionEvent actionEvent) throws IOException {
        Customer selectedCustomer = (Customer) customersTable.getSelectionModel().getSelectedItem();
        ViewCustomerController.currentCustomer = selectedCustomer;
        LoginScreen.changeScreen("viewcustomer");

    }
    public void toReports(ActionEvent actionEvent) throws IOException {
        LoginScreen.changeScreen("reports");
    }
}
