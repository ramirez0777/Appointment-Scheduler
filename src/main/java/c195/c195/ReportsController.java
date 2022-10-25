package c195.c195;

import helper.Queries;
import javafx.beans.property.Property;
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
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ReportsController implements Initializable {

    public TableView contactTable, typeTable, monthTable;
    public ComboBox contact;
    public TableColumn typeColumn, typeTotalColumn, monthColumn, monthTotalColumn, idColumn, titleColumn, appTypeColumn, descriptionColumn, startColumn, endColumn, customerIdColumn;
    ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            appointments = Queries.gatherAppointments();
        } catch (SQLException e) {
        }

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
    }

    public void appointmentsByContact() throws SQLException {
        int id = Queries.getContactId(contact.getValue().toString());
        contactAppointments = Queries.gatherContactAppointments(id);
        contactTable.setItems(contactAppointments);
    }

    public void toMainMenu() throws IOException{
        LoginScreen.changeScreen("mainmenu");
    }
}
