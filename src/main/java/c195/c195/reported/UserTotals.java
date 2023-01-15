package c195.c195.reported;

/**userTotals class is used for the reports screen. Keeps track of user creating appointments and how many they have created.*/

public class UserTotals extends ReportType{
    /**Username of the user being counted.*/
    private String username;
    /**Total times the user has created an appointment.*/
    private int total;

    /**Sets username and sets total to 1
     * @param username Username.
     * */
    public UserTotals(String username){
        this.username = username;
        this.total = 1;
    }

    /**Returns username
     * @return Returns the username.
     */
    public String getUsername(){
        return this.username;
    }
}
