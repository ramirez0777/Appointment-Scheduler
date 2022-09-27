package c195.c195;

import helper.Queries;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Customers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    public TableView customerTable;
    public TableColumn customers;
    public TableColumn location;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Queries.gatherCustomers();
        } catch (SQLException e){}

        Customer brian = new Customer(1, "brian", "street", 1, "zip", "801");
        customers.setCellValueFactory(new PropertyValueFactory<>("name"));

        location.setCellValueFactory(new PropertyValueFactory<>("division"));
        customerTable.setItems(Customers.getAllCustomers());

    }
}
