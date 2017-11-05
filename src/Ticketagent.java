import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import messages.*;

import java.util.ArrayList;
import java.util.Random;

public class Ticketagent extends AbstractActor {

    private ArrayList<ActorRef> seatManagers = new ArrayList<>();
    private Random random = new Random();


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(SeatReferences.class, msg -> {
                    seatManagers = msg.getSeatmanagers();
                })
                .match(Reserve.class, msg -> {
                    System.out.println("Let me see what i can do" + getSender());
                    IsAvailable message = new IsAvailable(msg.getNumberofseats(), getSender());
                    ActorRef seatManager = seatManagers.get(msg.getSection()-1);
                    seatManager.tell(message, getSelf());

                })
                .match(Available.class, msg -> {
                    msg.getCustomer().tell(msg, getSelf());
                })
                .match(NotAvailable.class, msg -> {
                    ActorRef customer = msg.getCustomer();
                    NotAvailable message = new NotAvailable(customer);
                    customer.tell(message, getSelf());

                })
                .match(Buy.class, msg -> {
                    System.out.println("Allright you can pay here");
                    int i = random.nextInt(10)+1;
                    if (i<8){
                        System.out.println("Succesfull Payment");
                        ActorRef seatManager = seatManagers.get(msg.getSection()-1);
                        seatManager.tell(msg, getSelf());
                    }else{
                        System.out.println("Payment failed");
                        getSender().tell("Failed", getSelf());
                        ActorRef seatManager = seatManagers.get(msg.getSection()-1);
                        Cancel cancel = new Cancel(msg.getSection(),msg.getNumberofseats(),msg.getCustomer());
                        seatManager.tell(cancel,getSelf());
                    }
                })
                .match(Reservation.class, msg -> {
                    Reservation message = new Reservation(msg.getSeats(),msg.getCustomer());
                    msg.getCustomer().tell(message, getSelf());
                })
                .match(Cancel.class, msg -> {
                    System.out.println("Allright we will cancel the tickets");
                    ActorRef seatManager = seatManagers.get(msg.getSection()-1);
                    seatManager.tell(msg,getSelf());
                })
                .matchAny(object ->{
                    System.out.println("Wrong Message");
                })
                .build();
    }


    public void preStart(){
        System.out.println("Ticketseller created");
    }

    public void postStop(){
        System.out.println("Ticketseller removed");
    }
}