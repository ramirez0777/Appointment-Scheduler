package c195.c195;

/**userTotals class is used for the reports screen. Keeps track of user creating appointments and how many they have created.*/

public class userTotals {
    /**Username of the user being counted.*/
    private String username;
    /**Total times the user has created an appointment.*/
    private int total;

    /**Sets username and sets total to 1
     * @param username Username.
     * */
    public userTotals(String username){
        this.username = username;
        total = 1;
    }

    /**Returns username
     * @return Returns the username.
     */
    public String getUsername(){
        return this.username;
    }

    /**Returns total
     * @return Returns the total.
     * */
    public int getTotal(){
        return this.total;
    }

    /**Increases total by 1*/
    public void incrementTotal(){
        this.total += 1;
    }
}
