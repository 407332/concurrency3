import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import messages.*;

import java.util.ArrayList;

public class SeatManager extends AbstractActor {

    private int section;
    private ArrayList<Seat> seats = new ArrayList<>();
    private ActorRef ticketseller;

    private SeatManager(int section, ActorRef ticketseller){
        this.section = section;
        this.ticketseller = ticketseller;

    }

    public static Props prop(int section, ActorRef ref ) {
        return Props.create(SeatManager.class, section, ref);
    }

    public Receive createReceive() {
        return receiveBuilder()
                .match(IsAvailable.class, msg -> {
                    if (seats.size() > msg.getNumberofseats()){
                        System.out.println("I have available seats");
                        ActorRef customer = msg.getCustomer();
                        Available message = new Available(customer);

                        ticketseller.tell(message, getSelf());

                    }else{
                        System.out.println("I don't have any space for you");
                        ActorRef customer = msg.getCustomer();
                        NotAvailable message = new NotAvailable(customer);
                        ticketseller.tell(message, getSelf());
                    }

                })
                .match(Bought.class, msg -> {
                    System.out.println("Allright the seats are reserved");

                })
                .match(Cancel.class, msg -> {
                    System.out.println("Allright I'll remove the reservation");

                })
                .build();
    }

    public void preStart(){
        addSeats();
    }

    public void addSeats(){
        for (int i=0; i < 40; i++){
            Seat seat = new Seat(section,i);
            seats.add(seat);
        }
    }

    public void reserve(int n) {
        int q = 0;
        while (q < n)
            for (int i = 0; i < seats.size(); i++) {

            }
    }
}

