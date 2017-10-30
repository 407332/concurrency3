import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import messages.*;

import java.util.ArrayList;
import java.util.Random;

public class TicketBureau extends AbstractActor {

    private ArrayList<ActorRef> seatManagers = new ArrayList<>();
    private Random random = new Random();

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Reserve.class, msg -> {
                    System.out.println("Let me see what i can do" + getSender());
                    IsAvailable message = new IsAvailable(msg.getNumberofseats(), getSender());
                    ActorRef seatManager = seatManagers.get(msg.getSection()-1);
                    seatManager.tell(message, getSelf());

                })
                .match(Available.class, msg -> {
                    ActorRef customer = msg.getCustomer();
                    Available message = new Available(customer);
                    customer.tell(message, getSelf());
                })
                .match(NotAvailable.class, msg -> {
                    ActorRef customer = msg.getCustomer();
                    NotAvailable message = new NotAvailable(customer);
                    customer.tell(message, getSelf());
                })
                .match(Bought.class, msg -> {
                    System.out.println("Allright you can pay here");
                    int i = random.nextInt(10)+1;
                    if (i<9){
                        System.out.println("Succesfull Payment");
                        ActorRef seatManager = seatManagers.get(msg.getSection()-1);
                        seatManager.tell(msg, getSelf());
                    }else{
                        System.out.println("Payment failed");
                        getSender().tell("Failed", getSelf());
                    }

                })
                .match(Cancel.class, msg -> {

                })
                .match(String.class, msg -> msg.equals("Start"), msg ->{
                    generateSeatmanagers();
                })
                .build();
    }

    private void generateSeatmanagers(){
        for (int i=0; i <= 6; i++){
            ActorRef temp = getContext().actorOf(SeatManager.prop(i, getSelf()), "SM"+ i);
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