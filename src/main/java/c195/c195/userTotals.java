package c195.c195;

public class userTotals {
    private String username;
    private int total;

    public userTotals(String username){
        this.username = username;
        total = 1;
    }

    public String getUsername(){
        return this.username;
    }

    public int getTotal(){
        return this.total;
    }

    public void incrementTotal(){
        this.total += 1;
    }
}
