import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import messages.*;

public class Customer extends AbstractActor {

    private int section = 1;
    private int numberofseats = 2;
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
                    Bought message = new Bought(section,numberofseats,ticketseller);
                    ticketseller.tell(message, getSelf());
                    System.out.println("I want to buy "+ numberofseats + " please.");
                })
                .match(NotAvailable.class, msg -> {
                    System.out.println("Owh well to bad.");
                })
                .match(Bought.class, msg -> {
                    System.out.println("Awesome!");

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
