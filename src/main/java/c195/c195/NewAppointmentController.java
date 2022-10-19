package c195.c195;

import helper.Queries;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.converter.LocalDateTimeStringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NewAppointmentController implements Initializable {
    public ComboBox startTime;
    public ComboBox endTime;
    public ComboBox contacts;
    public ArrayList<String>  availableTimes = new ArrayList<String>();
    public DatePicker startDate;
    public DatePicker endDate;
    public TextField titleField;
    public TextField descriptionField;
    public TextField locationField;
    public TextField typeField;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillInTimes();
        startTime.getItems().addAll(availableTimes);
        endTime.getItems().addAll(availableTimes);
        try {
            contacts.getItems().addAll(Queries.gatherContacts());
        } catch (SQLException e) {
        }
    }

    public void toViewCustomer() throws IOException{
        LoginScreen.changeScreen("viewcustomer");
    }

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

        if(Queries.overlappingAppointments(startLDT, endLDT)){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Customer already has an appointment at this time");
            alert.showAndWait();
            return;
        }

        Queries.insertAppointment(titleField.getText(), descriptionField.getText(), locationField.getText(), typeField.getText(), LoginScreen.convertTimeToUTC(startLDT), LoginScreen.convertTimeToUTC(endLDT), contactId);
        LoginScreen.changeScreen("viewcustomer");
    }
}
