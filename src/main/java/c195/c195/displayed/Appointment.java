package c195.c195.displayed;

import c195.c195.LoginScreen;
import helper.Queries;

import java.sql.SQLException;
import java.time.LocalDateTime;

/** This class is used for appointments. Has attributes that correlate to the database appointments and holds some in different types such as time is held as a String to display and as a LocalDateTime object. Class also contains getters and setters for all attributes.*/
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
    private String userName;
    private LocalDateTime startLDT;
    private LocalDateTime endLDT;

    /** Constructor for the class takes in most of the attributes. Some get converted to other types such as time. Some attributes are cross referenced against the database to get a correlated attribute such as contact and contactId.
     * @param id Appointment Id
     * @param title Appointment Title
     * @param location Appointment Location
     * @param contactId Contact ID of Appointment
     * @param type Appointment Type
     * @param startTime Appointment start time and date
     * @param endTime Appointment end time and date
     * @param customerId Customer ID of Appointment
     * @param userId User ID of Appointmnet
     * */
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
        setUserName();
        this.startLDT = LoginScreen.convertToLDT(this.startTime);
        this.endLDT = LoginScreen.convertToLDT(this.endTime);
    }

    /**Returns the Appointment ID.
     * @return Returns Appointment ID
     * */
    public int getAppointmentId(){
        return this.id;
    }

    /**Returns the Appointment ID for tables.
     * @return Returns appointment id.
     * */
    public int getId(){return this.id;}

    /**Returns the title of the Appointment.
     * @return Returns appointment title.
     * */
    public String getTitle(){
        return this.title;
    }

    /**Returns the description of the Appointment.
     * @return Returns appointment description.
     * */
    public String getDescription(){
        return this.description;
    }

    /**Returns the location of the Appointment.
     * @return Returns appointment location
     * */
    public String getLocation(){
        return this.location;
    }

    /**Returns the Contact Name of the Appointment.
     * @return Returns contact of appointment.
     * */
    public String getContact(){
        return this.contact;
    }

    /**Returns the type of the Appointment.
     * @return Returns type of appointment.
     * */
    public String getType(){
        return this.type;
    }

    /**Returns the Start Time and Date of the appointment as a String to display; already converted to the local time zone.
     * @return Returns the start time of appointment as String.
     * */
    public String getStartTime(){
        return this.startTime;
    }

    /**Returns the End Time and Date of the apointment as a String to display; already converted to the local time zone.
     * @return Returns the end time of appointment as String.
     * */
    public String getEndTime(){
        return this.endTime;
    }

    /**Returns the Customer Id of the Appointment.
     * @return Returns the customer ID of appointment.
     * */
    public int getCustomerId(){
        return this.customerId;
    }

    /**Returns the User Id of the Appointment.
     * @return Returns the user id of appointment.
     * */
    public int getUserId(){
        return this.userId;
    }

    /**Returns Contact Id of the appointment
     * @return Returns the contact id of the appointment
     * */
    public int getContactId(){
        return this.contactId;
    }

    /**Returns the Username of the creator of the Appointment.
     * @return Returns the username of the appointment.
     * */
    public String getUserName(){
        return this.userName;
    }

    /**Returns the Start Time and Date as LocalDateTime object already converted to local time zone.
     * @return Returns the start time as a LocalDateTime object.
     * */
    public LocalDateTime getStartLDT(){return this.startLDT;}

    /**Returns End Time and Date as LocalDateTime object already converted to local time zone.
     * @return Returns the end time as a LocalDateTime object.
     * */
    public LocalDateTime getEndLDT(){return this.endLDT;}

    /**Sets the Title of the appointment.
     * @param title Title of the appointment.
     * */
    public void setTitle(String title){
        this.title = title;
    }

    /**Sets the description of the appointment.
     * @param description Description of the appointment.
     * */
    public void setDescription(String description){
        this.description = description;
    }

    /**Sets the location of the appointment.
     * @param location Location of the appointment.
     * */
    public void setLocation(String location){
        this.location = location;
    }

    /**Sets the contact Id and contact name of the appointment.
     * @param contactId Contact Id of the appointment.
     * */
    public void setContact(int contactId) throws SQLException {

        this.contactId = contactId;
        this.contact = Queries.getContactName(contactId);
    }

    /**Sets the type of the appointment.
     * @param type Type of appointment.
     * */
    public void setType(String type){
        this.type = type;
    }

    /**Sets the start time of the appointment as a string to display.
     * @param startTime Start time and date of the appointment as a String with this format "yyyy-MM-dd HH:mm:ss"
     * */
    public void setStartTime(String startTime) {
        this.startTime = LoginScreen.convertTimeToLocal(startTime);
    }

    /**Sets the end time of the appointment as a string to display.
     * @param endTime End time and date of the appointment as a String with this format "yyyy-MM-dd HH:mm:ss"
     * */
    public void setEndTime(String endTime){
        this.endTime = LoginScreen.convertTimeToLocal(endTime);
    }

    /**Sets the User id of the appointment.
     * @param userId The user id of the appointment.
     * */
    public void setUserId(int userId){
        this.userId = userId;
    }

    /**Sets the username of the creator of the appointment, by comparing it's ID to the table on the database.*/
    public void setUserName() throws SQLException {
        this.userName = Queries.getUsername(this.userId);
    }
}
