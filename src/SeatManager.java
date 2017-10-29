import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import messages.*;

public class SeatManager extends AbstractActor {

    private int section;
    private int numberOfSeats;
    private int seatsavailable;
    private ActorRef customer = null;
    private ActorRef ticketseller;

    private SeatManager(int section, int numberOfSeats, ActorRef ticketseller){
        this.section = section;
        this.numberOfSeats = numberOfSeats;
        seatsavailable = numberOfSeats;
        this.ticketseller = ticketseller;
    }

    public void preStart(){
        System.out.println("Seatmanager created");
    }

    public void postStop(){
        System.out.println("Seatmanager removed");
    }


    public Receive createReceive() {
        return receiveBuilder()
                .match(Reserve.class, msg -> {

                })
                .match(IsAvailable.class, msg -> {

                })
                .match(Cancel.class, msg -> {

                })
                .build();
    }
}
