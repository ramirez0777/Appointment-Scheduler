package c195.c195.reported;

/**Type class is used for the reports screen. Keeps track of types used in appointments and how many of each*/
public class Type extends ReportType{
    private String type;
    private int total;

    /**Constructor creates type name and initiates total at 1
     * @param type The type of appointment.
     * */
    public Type(String type){
        this.type = type;
        this.total = 1;
    }

    /**Returns type
     * @return Returns the type.
     * */
    public String getType(){
        return this.type;
    }
}
