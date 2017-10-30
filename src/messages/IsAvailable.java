package messages;

public final class IsAvailable {
    private int numberofseats;

    public IsAvailable(int numberofseats){
        this.numberofseats = numberofseats;
    }

    public int getNumberofseats(){
        int var = numberofseats;
        return var;
    }
}
