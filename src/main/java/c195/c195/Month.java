package c195.c195;

/**Month class is used for the reports screen. Keeps track of months used in appointments and how many of each*/
public class Month {
    private String month;
    private int total;

    /**Constructor creates month name and initiates total at 1.
     * @param month Month name.
     * */
    public Month(String month){
        this.month = month;
        total = 1;
    }

    /**Returns month.
     * @return Returns month name.
     * */
    public String getMonth(){
        return this.month;
    }

    /**Returns total.
     * @return Returns total amount of times this month has been used.
     * */
    public int getTotal(){
        return this.total;
    }

    /**Increases total by 1*/
    public void incrementTotal(){
        total += 1;
    }
}
