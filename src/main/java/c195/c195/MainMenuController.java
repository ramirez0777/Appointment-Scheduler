package c195.c195;

import c195.c195.displayed.Appointment;
import c195.c195.displayed.Customer;
import helper.Queries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**This is the controller for the main menu screen. Main menu displays the customers table and has buttons that change the screen to add customer, update customer, view customer appointments, view all appointments, and view reports. */
public class MainMenuController implements Initializable {
    /**Table that displays all customers.*/
    public TableView customersTable;
    /**Column that displays customer names.*/
    public TableColumn customers;
    /**Column that displays customer first name divisions.*/
    public TableColumn location;
    /**List of Customers to be displayed on customersTable*/
    public ObservableList<Customer> customersList = FXCollections.observableArrayList();
    /**Text Field for user to search customers by name.**/
    public TextField customerSearch;

    /**When screen is initialized it fills in the table with all the customers*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            customersList = Queries.gatherCustomers();
        } catch (SQLException e) {
        }

        //Customer brian = new Customer(1, "brian", "street", 1, "zip", "801");
        customers.setCellValueFactory(new PropertyValueFactory<>("name"));
        location.setCellValueFactory(new PropertyValueFactory<>("division"));
        customersTable.setItems(customersList);
    }

    /**This method is used to delete customers from the database. It shows a confirmation box to delete and deletes all appointments customer has then the customer. A customer must be selected on the table for this not to show an error. */
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

    /**Switches to the new customer screen*/
    public void toNewCustomer() throws IOException {
        LoginScreen.changeScreen("newcustomer");
    }

    /**Switches to the update customer screen. Verifies a customer is selected in the table and shows an error if not. It then passes that customer over to the customer screen so that it can be updated*/
    public void toUpdateCustomer(ActionEvent actionEvent) throws IOException {
        Customer selectedCustomer = (Customer) customersTable.getSelectionModel().getSelectedItem();
        if(selectedCustomer == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a customer to update");
            alert.showAndWait();
        }
        else {
            UpdateCustomerController.currentCustomer = selectedCustomer;
            LoginScreen.changeScreen("updatecustomer");
        }
    }

    /**Switches to the view Customer Screen that displays appointments. Shows an error if customer is not selected in table. It passes the selected customer onto the view customer page.*/
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

    /**Switches to the reports screen*/
    public void toReports(ActionEvent actionEvent) throws IOException {
        LoginScreen.changeScreen("reports");
    }

    /**Switches back to the all appointments screen*/
    public void toAllAppointments() throws IOException{
        LoginScreen.changeScreen("allappointments");
    }

    /**When a user types in textbox and clicks enter it will search the customer list for names that contain the string inputted. It will then refresh the customer table with the results.**/
    public void searchCustomer() {
        String search = customerSearch.getText();
        ObservableList<Customer> customersSearched = FXCollections.observableArrayList();

        for(Customer customer : customersList){
            if(customer.getName().toLowerCase().contains(search.toLowerCase())){
                customersSearched.add(customer);
            }
        }
        customersTable.setItems(customersSearched);
    }
}
