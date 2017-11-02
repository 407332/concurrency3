import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import messages.*;
import java.util.Random;

import java.util.ArrayList;

public class Customer extends AbstractActor {

    private Random random = new Random();
    private int section = random.nextInt(7)+1;
    private int numberofseats = random.nextInt(4)+1;
    private ActorRef ticketseller;

    private Customer(ActorRef ticketseller){
        this.ticketseller = ticketseller;
    }

    public static Props prop( ActorRef ref ) {
        return Props.create(Customer.class, ref);
    }
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Available.class, msg -> {
                    Buy message = new Buy(section,numberofseats,getSelf());
                    ticketseller.tell(message, getSelf());
                    System.out.println("I want to buy "+ numberofseats + " please.");
                })
                .match(NotAvailable.class, msg -> {
                    System.out.println("Owh well too bad.");
                })
                .match(Reservation.class, msg -> {
                    System.out.println(msg.getSeats());
                    int i = random.nextInt(10)+1;
                    if (i>8){
                        System.out.println("I don't want my tickets anymore");
                        Cancel cancel = new Cancel(section, numberofseats, getSelf());
                        ticketseller.tell(cancel, getSelf());
                    }
                    section = random.nextInt(7)+1;
                    numberofseats = random.nextInt(4)+1;
                    Reserve message = new Reserve(section,numberofseats,getSelf());
                    ticketseller.tell(message, getSelf());
                    System.out.println("I want "+ numberofseats + " tickets in section " + section + " please.");

                })
                .build();
    }

    public void preStart(){
        System.out.println("Customer created");
        Reserve message = new Reserve(section,numberofseats,getSelf());
        ticketseller.tell(message, getSelf());
        System.out.println("I want "+ numberofseats + " tickets in section " + section + " please.");
    }

    public void postStop(){
        System.out.println("Customer removed");
    }

}
