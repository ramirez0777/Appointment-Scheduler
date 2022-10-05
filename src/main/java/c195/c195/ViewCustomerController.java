package c195.c195;

import helper.Queries;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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

    public void toMainMenu() throws IOException {
        LoginScreen.changeScreen("mainmenu");
    }
}
