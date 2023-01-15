package c195.c195;

import c195.c195.displayed.Appointment;
import c195.c195.reported.Month;
import c195.c195.reported.Type;
import c195.c195.reported.UserTotals;
import helper.Queries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**Controller for reports screen. Fills in reports.*/
public class ReportsController implements Initializable {
    /**Tables to display users, contacts, types and months.*/
    public TableView userTable, contactTable, typeTable, monthTable;
    /**Selection box for user to select contact.*/
    public ComboBox contact;
    /**Columns for all the tables.*/
    public TableColumn userColumn, userTotalColumn, typeColumn, typeTotalColumn, monthColumn, monthTotalColumn, idColumn, titleColumn, appTypeColumn, descriptionColumn, startColumn, endColumn, customerIdColumn;
    /**List of all appointments.*/
    ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    /**List of appointments sorted by contact selected by user*/
    ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();

    /**When screen is started it fills in the tables for month, type and user. Counts totals and fills them in. Fills in combobox for contacts of possible contacts.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            appointments = Queries.gatherAppointments();
        } catch (SQLException e) {
        }

        //Report1
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        appTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        try {
            contact.getItems().addAll(Queries.gatherContacts());
        } catch (SQLException e) {}

        ObservableList<Type> types = FXCollections.observableArrayList();
        ObservableList<Month> months = FXCollections.observableArrayList();

        for(Appointment currentAppointment : appointments){
            boolean typeExisted = false;
            boolean monthExisted = false;

            if(types.size() == 0){
                types.add(new Type(currentAppointment.getType()));
            }
            else{
                for(int i = 0; i < types.size(); i++){
                    if(types.get(i).getType().toLowerCase().equals(currentAppointment.getType().toLowerCase())){
                        types.get(i).incrementTotal();
                        typeExisted = true;
                    }
                }
                if(!typeExisted){
                    types.add(new Type(currentAppointment.getType()));
                    typeExisted = false;
                }

            }

            if(months.size() == 0){
                months.add(new Month(currentAppointment.getStartLDT().getMonth().toString()));
            }
            else{
                for(int i = 0; i < months.size(); i++){
                    if(months.get(i).getMonth().equals(currentAppointment.getStartLDT().getMonth().toString())){
                        months.get(i).incrementTotal();
                        monthExisted = true;
                    }
                }
                if(!monthExisted){
                    months.add(new Month(currentAppointment.getStartLDT().getMonth().toString()));
                    monthExisted = false;
                }
            }
        }

        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeTotalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        typeTable.setItems(types);

        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        monthTotalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        monthTable.setItems(months);

        //report3
        ObservableList<UserTotals> users = FXCollections.observableArrayList();
        for(Appointment currentAppointment : appointments){
            boolean userExisted = false;
            if(users.size() == 0){
                users.add(new UserTotals(currentAppointment.getUserName()));
            }
            else{
                for(int i = 0; i < users.size(); i++){
                    if(users.get(i).getUsername().equals(currentAppointment.getUserName())){
                        users.get(i).incrementTotal();
                        userExisted = true;
                    }
                }
                if(!userExisted){
                    users.add(new UserTotals(currentAppointment.getUserName()));
                }
            }
        }

        userColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        userTotalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        userTable.setItems(users);
    }

    /**This method is ran when a contact is selected. Fills in table with all appointments assigned to that contact. Uses Lambda statement to filter appointments by the Contact Id then displaying the results on the table.*/
    public void appointmentsByContact() throws SQLException {
        //report2
        int id = Queries.getContactId(contact.getValue().toString());
        //Lambda
        contactAppointments = appointments.stream().filter(a -> a.getContactId() == id).collect(Collectors.toCollection(FXCollections::observableArrayList));
        contactTable.setItems(contactAppointments);
    }

    /**Changes screen back to the main menu*/
    public void toMainMenu() throws IOException{
        LoginScreen.changeScreen("mainmenu");
    }
}
