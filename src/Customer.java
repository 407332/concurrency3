import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import messages.*;

public class Customer extends AbstractActor {

    private int section;
    private int seatsreserved;
    private ActorRef ticketseller;


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Available.class, msg -> {

                })
                .match(NotAvailable.class, msg -> {

                })
                .match(Bought.class, msg -> {

                })
                .build();
    }

    public void preStart(){
        System.out.println("Customer created");
    }

    public void postStop(){
        System.out.println("Customer removed");
    }

}
