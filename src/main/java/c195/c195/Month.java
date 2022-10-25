package c195.c195;

public class Month {
    private String month;
    private int total;

    public Month(String month){
        this.month = month;
        total = 1;
    }

    public String getMonth(){
        return this.month;
    }

    public int getTotal(){
        return this.total;
    }

    public void incrementTotal(){
        total += 1;
    }
}
