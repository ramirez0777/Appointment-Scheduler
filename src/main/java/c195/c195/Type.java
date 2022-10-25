package c195.c195;

public class Type {
    private String type;
    private int total;

    public Type(String type){
        this.type = type;
        total = 1;
    }

    public String getType(){
        return this.type;
    }

    public int getTotal(){
        return this.total;
    }

    public void incrementTotal(){
        total += 1;
    }
}
