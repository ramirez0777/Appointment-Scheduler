package c195.c195;

import c195.c195.displayed.Appointment;
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

/** This class is the controller for the allappointments-view page. It displays all appointments from the Database and allows you to sort by week and month.*/
public class AllappointmentsController implements Initializable {
    /**List of all appointments.*/
    ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    /**Table to display appointments.*/
    public TableView<Appointment> appointmentTable;
    /**Column to display appointment ids*/
    public TableColumn idColumn;
    /**Column to display appointment titles*/
    public TableColumn titleColumn;
    /**Column to display appointment descrioptions*/
    public TableColumn descriptionColumn;
    /**Column to display appointment locations*/
    public TableColumn locationColumn;
    /**Column to display appointment appointmentss*/
    public TableColumn contactColumn;
    /**Column to display appointment typess*/
    public TableColumn typeColumn;
    /**Column to display appointment start times*/
    public TableColumn startColumn;
    /**Column to display appointment end times*/
    public TableColumn endColumn;
    /**Column to display appointment customer ids*/
    public TableColumn customerIdColumn;
    /**Column to display appointment user ids*/
    public TableColumn userIdColumn;
    /**Radio button to filter table to show all appointments.*/
    public RadioButton all;
    /**Radio button to filter table by month.*/
    public RadioButton monthly;
    /**Radio button to filter table by week.*/
    public RadioButton weekly;

    /**This class implements Initializable. Before the page is loaded a notification pops up and shows whether or not there is an appointment within 15 minutes of getting to the page. It then fills in the Table with information from appointments received from the Database. */
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

    /** This method is ran when the user click on the Main Menu Button. It changes the screen to the Main Menu screen. */
    public void toMainMenu() throws IOException {
        LoginScreen.changeScreen("mainmenu");
    }

    /** This method is used to filter the appointments by showing all, a weekly view which shows the current days appointments + 7 days, and a monthly view depending on the current month the user is in. These are selected by radio buttons and All is shown by default. This method uses a Lambda expression. This Lambda expression sorts through the appointments and returns an ObservableList of appointments that are the same month as the month the user is currently in. The weeklyh sort is done by checking if the appointment is in between the current time and current time plus 7 days.  */
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
