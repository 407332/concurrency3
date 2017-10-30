import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import messages.*;

import java.util.ArrayList;

public class TicketSeller extends AbstractActor {

    private ActorRef customer;
    private ActorRef seatManager;
    private ArrayList<ActorRef> seatManagers = new ArrayList<>();


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Reserve.class, msg -> {
                    int section = msg.getSection();

                    customer = getSender();
                    int numberofseats = msg.getNumberofseats();
                    IsAvailable message = new IsAvailable(numberofseats);
                    seatManager.tell(message, getSelf());
                })
                .match(Buy.class, msg -> {

                })
                .match(Available.class, msg -> {

                })
                .match(NotAvailable.class, msg -> {

                })
                .match(Cancel.class, msg -> {

                })
                .match(String.class, msg -> msg.equals("Start"), msg ->{
                    generateSeatmanagers();
                })
                .build();
    }

    private void generateSeatmanagers(){
        for (int i=1; i <= 7; i++){
            ActorRef temp = getContext().actorOf(SeatManager.prop(i, 40, getSelf()), "SM"+ i);
            seatManagers.add(temp);
            System.out.println("Seatmanager "+ i + "created");
        }
    }

    public void preStart(){
        System.out.println("Ticketseller created");
    }

    public void postStop(){
        System.out.println("Ticketseller removed");
    }
}