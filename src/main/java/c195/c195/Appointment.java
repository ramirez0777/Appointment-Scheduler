package c195.c195;

import helper.Queries;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class Appointment {
    private int id;
    private String title;
    private String description;
    private String location;
    private String contact;
    private int contactId;
    private String type;
    private String startTime;
    private String endTime;
    private int customerId;
    private int userId;
    private LocalDateTime startLDT;
    private LocalDateTime endLDT;

    public Appointment(int id, String title, String description, String location, int contactId, String type, String startTime, String endTime, int customerId, int userId) throws SQLException {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        setContact(contactId);
        this.type = type;
        setStartTime(startTime);
        setEndTime(endTime);
        this.customerId = customerId;
        this.userId = userId;
        this.startLDT = LoginScreen.convertoLDT(this.startTime);
        this.endLDT = LoginScreen.convertoLDT(this.endTime);
    }

    public int getAppointmentId(){
        return this.id;
    }
    public int getId(){return this.id;}

    public String getTitle(){
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }

    public String getLocation(){
        return this.location;
    }

    public String getContact(){
        return this.contact;
    }

    public String getType(){
        return this.type;
    }

    public String getStartTime(){
        return this.startTime;
    }

    public String getEndTime(){
        return this.endTime;
    }

    public int getCustomerId(){
        return this.customerId;
    }

    public int getUserId(){
        return this.userId;
    }

    public LocalDateTime getStartLDT(){return this.startLDT;}
    public LocalDateTime getEndLDT(){return this.endLDT;}

    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public void setContact(int contactId) throws SQLException {

        this.contactId = contactId;
        this.contact = Queries.getContactName(contactId);
    }

    public void setType(String type){
        this.type = type;
    }

    public void setStartTime(String startTime) {
        this.startTime = LoginScreen.convertTimeToLocal(startTime);
    }

    public void setEndTime(String endTime){
        this.endTime = LoginScreen.convertTimeToLocal(endTime);
    }

    public void setUserId(int userId){
        this.userId = userId;
    }
}
