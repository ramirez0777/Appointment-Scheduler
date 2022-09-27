package c195.c195;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Customer {
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    private int id;
    private String name;
    private String streetAddress;
    private int division;
    private String postalCode;
    private String phone;

    public Customer(int id, String name, String streetAddress, int division, String postalCode, String phone){
        this.id = id;
        this.name = name;
        this.streetAddress = streetAddress;
        this.division = division;
        this.postalCode = postalCode;
        this.phone = phone;
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

    public int getDivision(){
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

    public void setDivision(int division){
        this.division = division;
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
}
