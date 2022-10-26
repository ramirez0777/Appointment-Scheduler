package c195.c195;

import helper.Queries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

/**The Customer class has attributes that correlate with the Customer Table in the database. It also has an ObservableList of Appointments that are the customers.*/
public class Customer {
    private ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private int id;
    private String name;
    private String streetAddress;
    private String division;
    private int divisionId;
    private String postalCode;
    private String phone;

    /**The constructor sets all the attributes of the Customer.
     * @param id Customer id.
     * @param name Customer name.
     * @param streetAddress Customers streeet address.
     * @param division Customers first level division.
     * @param postalCode Customers postal code.
     * @param phone Customers phone number.
     * */
    public Customer(int id, String name, String streetAddress, int division, String postalCode, String phone) throws SQLException {
        this.id = id;
        this.name = name;
        this.streetAddress = streetAddress;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = division;
        setDivision(division);
    }

    /**Returns the customer Id
     * @return Returns customer Id.
     * */
    public int getCustomerId(){
        return this.id;
    }

    /**Returns the name of the Customer
     * @return Returns the customer id.
     * */
    public String getName(){
        return this.name;
    }

    /**Returns the street address of the customer.
     * @return Returns the street address.
     * */
    public String getStreetAddress(){
        return this.streetAddress;
    }

    /**Returns the division name of the customer.
     * @return Returns the divsion name.
     * * */
    public String getDivision(){
        return this.division;
    }

    /**Returns the postal code of the customer.
     * @return REturns the postal code.
     * */
    public String getPostalCode(){
        return this.postalCode;
    }

    /**Returns the phone number of the customer.
     * @return Returns the phone number.
     * */
    public String getPhone(){
        return this.phone;
    }

    /**Sets the name of the customer.
     * @param name Customer name.
     * */
    public void setName(String name){
        this.name = name;
    }

    /**Sets the street address of the customer.
     * @param streetAddress Customers street address.
     * */
    public void setStreetAddress(String streetAddress){
        this.streetAddress = streetAddress;
    }

    /**Sets the division id of the customer. References the database to set the division name of the customer
     * @param division Customer first level division.
     * */
    public void setDivision(int division) throws SQLException {
        this.divisionId = division;
        this.division = Queries.searchDivision(division, "division");
    }

    /**Sets the postal code of the customer
     * @param postalCode Customer postal code.
     *
     * */
    public void setPostalCode(String postalCode){
        this.postalCode = postalCode;
    }

    /**Sets the phone number of the customer.
     * @param phone Customer phone number.
     * */
    public void setPhone(String phone){
        this.phone = phone;
    }

    /**Returns the ObservableList of Appointments the customer has.
     * @return Returns all appointments customer has.
     * */
    public ObservableList<Appointment> getAppointments(){
        return allAppointments;
    }

    /**Adds an appointment to the allAppointments attribute.
     * @param newAppointment New Appointment getting added.
     * */
    public void addAppointment(Appointment newAppointment){
        allAppointments.add(newAppointment);
        //System.out.println(this.getName() + "'s appointment: " + this.getAppointments().size());
    }

    /**Returns the division id of the customer.
     * @return Returns the division id.
     * */
    public int getDivisionId(){
        return this.divisionId;
    }

    /**Removes all appointments from the allappointments attribute*/
    public void clearAppointments(){
        allAppointments.clear();
    }
}
