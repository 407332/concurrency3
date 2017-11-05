package messages;

public final class Reserve {
    private int section;
    private int numberofseats;

    public Reserve(int section, int numberofseats){
        this.section = section;
        this.numberofseats = numberofseats;
    }

    public int getSection(){
        return section;
    }
    public int getNumberofseats(){
        return numberofseats;
    }
}
