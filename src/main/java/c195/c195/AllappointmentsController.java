package c195.c195;

import helper.Queries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AllappointmentsController implements Initializable {
    ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    public TableView<Appointment> appointmentTable;
    public TableColumn idColumn;
    public TableColumn titleColumn;
    public TableColumn descriptionColumn;
    public TableColumn locationColumn;
    public TableColumn contactColumn;
    public TableColumn typeColumn;
    public TableColumn startColumn;
    public TableColumn endColumn;
    public TableColumn customerIdColumn;
    public TableColumn userIdColumn;
    public RadioButton all;
    public RadioButton monthly;
    public RadioButton weekly;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            appointments = Queries.gatherAppointments();
        } catch (SQLException e) {}

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime currentTime15 = currentTime.plusMinutes(15);
        String upcomingAppointments = "";

        for(Appointment appointment : appointments){
            if(appointment.getStartLDT().equals(currentTime) || (appointment.getStartLDT().isAfter(currentTime) && appointment.getStartLDT().isBefore(currentTime15))){
                upcomingAppointments = upcomingAppointments + "Appointment ID: " + appointment.getAppointmentId() + " Start Time: " + appointment.getStartTime() + "\n";
            }
        }

        if(!upcomingAppointments.equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, upcomingAppointments);
            alert.setHeaderText("Upcoming Appointments");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "There are no upcoming appointments within 15 Minutes");
            alert.setHeaderText("Upcoming Appointments");
            alert.showAndWait();
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        appointmentTable.setItems(appointments);
    }

    public void toMainMenu() throws IOException {
        LoginScreen.changeScreen("mainmenu");
    }

    public void filterAppointments(){
        ObservableList<Appointment> appointmentsFiltered = FXCollections.observableArrayList();
        if(monthly.isSelected()) {
            //LAMBDA
            appointmentsFiltered = appointments.stream().filter(a -> a.getStartLDT().getMonth().equals(LocalDateTime.now().getMonth())).collect(Collectors.toCollection(FXCollections::observableArrayList));
            appointmentTable.setItems(appointmentsFiltered);
        }
        else if (weekly.isSelected()) {
            LocalDateTime currentTime = LocalDateTime.now();
            LocalDateTime currentTime7 = currentTime.plusDays(7);
            for(Appointment app : appointments){
                if(app.getStartLDT().isBefore(currentTime7) && app.getStartLDT().isAfter(currentTime)){
                    appointmentsFiltered.add(app);
                }
            }
            appointmentTable.setItems(appointmentsFiltered);
        }else{
            appointmentTable.setItems(appointments);
        }
    }
}
