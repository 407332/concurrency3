import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import messages.*;

public class TicketSeller extends AbstractActor {

    private ActorRef customer;
    private ActorRef seatManager;


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Reserve.class, msg -> {

                })
                .match(Buy.class, msg -> {

                })
                .match(Available.class, msg -> {

                })
                .match(Cancel.class, msg -> {

                })
                .match(NotAvailable.class, msg -> {

                })
                .build();
    }
}
