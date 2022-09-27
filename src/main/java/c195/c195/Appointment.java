package c195.c195;

public class Appointment {
    private int id;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private String startTime;
    private String endTime;
    private int customerId;
    private int userId;

    public Appointment(int id, String title, String description, String location, String contact, String type, String startTime, String endTime, int customerId, int userId){
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerId = customerId;
        this.userId = userId;
    }

    public int getAppointmentId(){
        return this.id;
    }

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

    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public void setContact(String contact){
        this.contact = contact;
    }

    public void setType(String type){
        this.type = type;
    }

    public boolean setStartTime(String startTime){
        return false;
    }

    public boolean setEndTime(String endTime){
        return false;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }
}
