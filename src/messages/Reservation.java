package messages;
import akka.actor.ActorRef;
import java.util.ArrayList;
public final class Reservation {
    private ActorRef customer;
    private ArrayList<String> seats = new ArrayList<>();

    public Reservation(ArrayList<String> args, ActorRef customer){
        this.seats = args;
        this.customer = customer;
    }

    public ArrayList getSeats(){
        return seats;
    }

    public ActorRef getCustomer(){
        return customer;
    }
}
