import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.routing.ActorRefRoutee;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Routee;
import akka.routing.Router;
import messages.*;

import java.util.ArrayList;
import java.util.List;

public class TicketBureau extends AbstractActor {


    private ArrayList<ActorRef> seatManagers = new ArrayList<>();
    private Router router;

    {
        generateSeatmanagers();
        List<Routee> routees = new ArrayList<Routee>();
        for (int i = 0; i < 4; i++) {
            ActorRef temp = getContext().actorOf(Props.create(Ticketagent.class));
            getContext().watch(temp);
            routees.add(new ActorRefRoutee(temp));
            SeatReferences msg = new SeatReferences(seatManagers);
            temp.tell(msg,getSelf());
        }
        router = new Router(new RoundRobinRoutingLogic(), routees);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Reserve.class, msg -> {
                    router.route(msg,getSender());
                }).matchAny(object ->{
                    System.out.println("Wrong Message");
                })
                .build();
    }

    private void generateSeatmanagers(){
        for (int i=0; i <= 6; i++){
            ActorRef temp = getContext().actorOf(SeatManager.prop(i+1));
            seatManagers.add(temp);
            System.out.println("Seatmanager "+ (i+1) + "created");
        }
    }

    public void preStart(){
        System.out.println("Ticketbureau created");
    }

    public void postStop(){
        System.out.println("Ticketbureau removed");
    }
}