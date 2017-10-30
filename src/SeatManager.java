import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import messages.*;

public class SeatManager extends AbstractActor {

    private int section;
    private int numberOfSeats;
    private int seatsavailable;
    private ActorRef ticketseller;

    private SeatManager(int section, int numberOfSeats, ActorRef ticketseller){
        this.section = section;
        this.numberOfSeats = numberOfSeats;
        seatsavailable = numberOfSeats;
        this.ticketseller = ticketseller;

    }

    public static Props prop(int section, int seats, ActorRef ref ) {
        return Props.create(SeatManager.class, section, seats, ref);
    }
    public void preStart(){
        System.out.println("Seatmanager created");
    }

    public void postStop(){
        System.out.println("Seatmanager removed");
    }


    public Receive createReceive() {
        return receiveBuilder()
                .match(IsAvailable.class, msg -> {
                    if (seatsavailable > msg.getNumberofseats()){
                        ticketseller.tell("Available", getSelf());
                    }else{
                        ticketseller.tell("NotAvailable", getSelf());
                    }

                })
                .match(Reserve.class, msg -> {

                })
                .match(Cancel.class, msg -> {

                })
                .build();
    }

    public int getSection() {
        int var = section;
        return var;
    }

    public int getNumberOfSeats() {
        int var = numberOfSeats;
        return var;
    }

    public int getSeatsavailable() {
        int var = seatsavailable;
        return var;
    }

    public ActorRef getTicketseller() {
        ActorRef var = ticketseller;
        return var;
    }
}

