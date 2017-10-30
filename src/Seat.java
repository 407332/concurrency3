import akka.actor.ActorRef;

public class Seat {

    private int section;
    private int seatNumber;
    private ActorRef owner = null;

    public Seat(int section, int seatNumber) {
        this.section = section;
        this.seatNumber = seatNumber;
    }

    public int getSection() {
        int var = section;
        return var;
    }

    public int getSeatNumber() {
        int var = seatNumber;
        return var;
    }

    public ActorRef getOwner() {
        ActorRef var = owner;
        return var;
    }

    public void setOwner(ActorRef owner) {
        this.owner = owner;
    }
}
