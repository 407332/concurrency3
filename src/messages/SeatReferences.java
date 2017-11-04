package messages;
import akka.actor.ActorRef;
import java.util.ArrayList;

public final class SeatReferences {

    private ArrayList<ActorRef> args = new ArrayList<>();

    public SeatReferences(ArrayList<ActorRef> args){
        this.args = args;
    }

    public ArrayList<ActorRef> getSeatmanagers() {
        return args;
    }
}
