package c195.c195;

/**Type class is used for the reports screen. Keeps track of types used in appointments and how many of each*/
public class Type {
    private String type;
    private int total;

    /**Constructor creates type name and initiates total at 1
     * @param type The type of appointment.
     * */
    public Type(String type){
        this.type = type;
        total = 1;
    }

    /**Returns type
     * @return Returns the type.
     * */
    public String getType(){
        return this.type;
    }

    /**Returns total
     * @return Returns the total of this type.
     * */
    public int getTotal(){
        return this.total;
    }

    /**Increases total by 1*/
    public void incrementTotal(){
        total += 1;
    }
}
