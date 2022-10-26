package c195.c195;

import helper.Queries;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**Controller for the update appointment screen. Fills in appointment details and allows user to update appointment information*/
public class UpdateAppointmentController implements Initializable {
    /**Selection box for user to choose start time.*/
    public ComboBox startTime;
    /**Selection box for user to choose end time.*/
    public ComboBox endTime;
    /**Selection box for user to choose contact name.*/
    public ComboBox contacts;
    /**List of times that are available throughout the day.*/
    public ArrayList<String>  availableTimes = new ArrayList<String>();
    /**Date picker for user to select start date.*/
    public DatePicker startDate;
    /**Date picker for user to select end date.*/
    public DatePicker endDate;
    /**Text field for user to type in the title of appointment.*/
    public TextField titleField;
    /**Text field for user to type in the description of appointment.*/
    public TextField descriptionField;
    /**Text field for user to type in the location of appointment.*/
    public TextField locationField;
    /**text fiel for user to type in the type of appointment.*/
    public TextField typeField;
    /**Current appointment that is being updated. This is set by the view customer screen when moving over to this screen.*/
    public static Appointment currentAppointment;

    /**Fills in availableTimes array with times of the day in 15 minute intervals*/
    public void fillInTimes() {
        for (int i = 1; i <= 24; i++) {
            String time = "" + i + ":00";
            availableTimes.add(time);
            time = i + ":15";
            availableTimes.add(time);
            time = i + ":30";
            availableTimes.add(time);
            time = i + ":45";
            availableTimes.add(time);
        }
    }


    /**When scren is started it fills in all the fields from information of the appointment*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillInTimes();
        startTime.getItems().addAll(availableTimes);
        endTime.getItems().addAll(availableTimes);
        try {
            contacts.getItems().addAll(Queries.gatherContacts());
        } catch (SQLException e) {
        }

        titleField.setText(currentAppointment.getTitle());
        descriptionField.setText(currentAppointment.getDescription());
        locationField.setText(currentAppointment.getLocation());
        typeField.setText(currentAppointment.getType());
        contacts.getSelectionModel().select(currentAppointment.getContact());
        startDate.setValue(LocalDate.of(currentAppointment.getStartLDT().getYear(), currentAppointment.getStartLDT().getMonth(), currentAppointment.getStartLDT().getDayOfMonth()));
        endDate.setValue(LocalDate.of(currentAppointment.getEndLDT().getYear(), currentAppointment.getEndLDT().getMonth(), currentAppointment.getEndLDT().getDayOfMonth()));

        String startTimeString = "";
        String endTimeString = "";
        if(currentAppointment.getStartLDT().getMinute() == 0) {
             startTimeString = currentAppointment.getStartLDT().getHour() + ":0" + currentAppointment.getStartLDT().getMinute();
        }
        else {
             startTimeString = currentAppointment.getStartLDT().getHour() + ":" + currentAppointment.getStartLDT().getMinute();
        }

        if(currentAppointment.getEndLDT().getMinute() == 0){
             endTimeString = currentAppointment.getEndLDT().getHour() + ":0" + currentAppointment.getEndLDT().getMinute();
        }
        else{
             endTimeString = currentAppointment.getEndLDT().getHour() + ":" + currentAppointment.getEndLDT().getMinute();
        }

        startTime.getSelectionModel().select(startTimeString);
        endTime.getSelectionModel().select(endTimeString);
    }

    /**Changes screen back to the view customer screen*/
    public void toViewCustomer() throws IOException{
        LoginScreen.changeScreen("viewcustomer");
    }

    /**Runs when save Appointment button is clicked. Verifies all fields are filled in and valid. If not shows an error. If everything is correct it updates the appointment in the database and switches screen back to the view customer screen.*/
    public void addAppointment() throws IOException, SQLException{
        if(titleField.getText().equals("") || descriptionField.getText().equals("") || locationField.getText().equals("") || typeField.getText().equals("") || contacts.getSelectionModel().isEmpty() || startTime.getSelectionModel().isEmpty() || endTime.getSelectionModel().isEmpty() || endDate.getValue() == null || startDate.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill in all fields");
            alert.showAndWait();
            return;
        }



        String st = startTime.getValue().toString();
        String et = endTime.getValue().toString();
        if(st.length() == 4){
            st = "0" + st;
        }
        if(et.length() == 4){
            et = "0" + et;
        }

        st = st + ":00";
        et = et + ":00";

        LocalDateTime startLDT = LoginScreen.convertToLDT(startDate.getValue().toString(), st);
        LocalDateTime endLDT = LoginScreen.convertToLDT(endDate.getValue().toString(), et);

        if(!LoginScreen.isCompanyOpen(startLDT, endLDT)){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a Date & Time on a weekday between 8am - 10pm EST.");
            alert.showAndWait();
            return;
        }
        int contactId = Queries.getContactId(contacts.getSelectionModel().getSelectedItem().toString());
        System.out.println(contactId);

        if(Queries.overlappingAppointments(startLDT, endLDT, currentAppointment.getAppointmentId())){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Customer already has an appointment at this time");
            alert.showAndWait();
            return;
        }

        System.out.println("Appointment Updated");
        Queries.updateAppointment(titleField.getText(), descriptionField.getText(), locationField.getText(), typeField.getText(), LoginScreen.convertTimeToUTC(startLDT), LoginScreen.convertTimeToUTC(endLDT), contactId);
        LoginScreen.changeScreen("viewcustomer");
    }
}
