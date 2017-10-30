package messages;

public final class Buy {
    private int section;
    private int numberofseats;

    public Buy(int section, int numberofseats){
        this.section = section;
        this.numberofseats = numberofseats;
    }

    public int getSection(){
        int var = section;
        return var;
    }
    public int getNumberofseats(){
        int var = numberofseats;
        return var;
    }
}
