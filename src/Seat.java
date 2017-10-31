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
        return section;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public ActorRef getOwner() {
        return owner;
    }

    public void setOwner(ActorRef owner) {
        this.owner = owner;
    }
}
