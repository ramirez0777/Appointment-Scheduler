package c195.c195.reported;

/**Month class is used for the reports screen. Keeps track of months used in appointments and how many of each*/
public class Month extends ReportType{
    private String month;
    private int total;

    /**Constructor creates month name and initiates total at 1.
     * @param month Month name.
     * */
    public Month(String month){
        this.month = month;
        this.total = 1;
    }

    /**Returns month.
     * @return Returns month name.
     * */
    public String getMonth(){
        return this.month;
    }

}
