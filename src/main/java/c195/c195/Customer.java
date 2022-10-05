package c195.c195;

import helper.Queries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class Customer {
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    private int id;
    private String name;
    private String streetAddress;
    private String division;
    private int divisionId;
    private String postalCode;
    private String phone;

    public Customer(int id, String name, String streetAddress, int division, String postalCode, String phone) throws SQLException {
        this.id = id;
        this.name = name;
        this.streetAddress = streetAddress;

        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = division;

        //This needs to be changed to the name instead of the id, so we can store it as a String that way gets take it back as that, but when saving it we cross reference the table with the id
        setDivision(division);
    }

    public int getCustomerId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getStreetAddress(){
        return this.streetAddress;
    }

    public String getDivision(){
        return this.division;
    }

    public String getPostalCode(){
        return this.postalCode;
    }

    public String getPhone(){
        return this.phone;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setStreetAddress(String streetAddress){
        this.streetAddress = streetAddress;
    }

    public void setDivision(int division) throws SQLException {
        this.divisionId = division;
        this.division = Queries.searchDivision(division, "division");
    }

    public void setPostalCode(String postalCode){
        this.postalCode = postalCode;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public ObservableList<Appointment> getAppointments(){
        return appointments;
    }

    public boolean addAppointment(Appointment newAppointment){
        return false;
    }

    public boolean deleteAppointment(Appointment selectedAppointment){
        return false;
    }

    public int getDivisionId(){
        return this.divisionId;
    }
}
