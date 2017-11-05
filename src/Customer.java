import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import messages.*;
import java.util.Random;

import java.util.ArrayList;

public class Customer extends AbstractActor {

    private int testsection = 7;

    private Random random = new Random();
    private int section = random.nextInt(testsection)+1;
    private int numberofseats = random.nextInt(4)+1;
    private ActorRef ticketseller;
    private ArrayList<String> seats = new ArrayList<>();

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
                    getSender().tell(message, getSelf());
                    System.out.println("I want to buy "+ numberofseats + " please.");
                })
                .match(NotAvailable.class, msg -> {
                    System.out.println("Owh well too bad.");
                    order();
                })
                .match(Reservation.class, msg -> {
                    int i = random.nextInt(10)+1;
                    if (i>8){
                        System.out.println("I don't want my tickets anymore");
                        Cancel cancel = new Cancel(section, numberofseats, getSelf());
                        getSender().tell(cancel, getSelf());
                    }else{
                        System.out.println(msg.getSeats());
                        String temp = "order"+ msg.getSeats();
                        seats.add(temp);
                    }
                    order();
                })
                .matchEquals("Failed", s ->{
                    System.out.println("I swear I had credit");
                    order();
                })
                .matchAny(object ->{
                    System.out.println("Wrong Message");
                })
                .build();
    }

    public void preStart(){
        System.out.println("Customer created");
        Reserve message = new Reserve(section,numberofseats);
        ticketseller.tell(message, getSelf());
        System.out.println("I want "+ numberofseats + " tickets in section " + section + " please.");
    }

    public void postStop(){
        System.out.println( getSelf()+ " owns " + seats);
        System.out.println("Customer removed");
    }


    public void order(){
        section = random.nextInt(testsection)+1;
        numberofseats = random.nextInt(4)+1;
        Reserve message = new Reserve(section,numberofseats);
        ticketseller.tell(message, getSelf());
        System.out.println("I want "+ numberofseats + " tickets in section " + section + " please.");
    }
}
