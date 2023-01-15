package c195.c195.reported;

/**Super class for reports. Reported items will extend this calss. **/
abstract class ReportType {
    /**Report total.*/
    private int total;

    /**Constructor. Sets total paramater.
     * **/
    public ReportType(){
        this.total = 1;
    }

    /**Returns total.
     * @return total parameter.
     * **/
    public int getTotal() {
        return total;
    }

    /**Increases total by 1*/
    public void incrementTotal(){
        this.total += 1;
    }
}
