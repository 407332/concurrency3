import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import messages.*;

import java.util.ArrayList;

public class SeatManager extends AbstractActor {

    private int section;
    private ArrayList<Seat> seats = new ArrayList<>();
    private ArrayList<Seat> takenseats = new ArrayList<>();

    private SeatManager(int section){
        this.section = section;
    }

    public static Props prop(int section) {
        return Props.create(SeatManager.class, section);
    }

    public Receive createReceive() {
        return receiveBuilder()
                .match(IsAvailable.class, msg -> {
                    if (seats.size() >= msg.getNumberofseats()){
                        System.out.println("I have available seats");
                        ActorRef customer = msg.getCustomer();
                        Available message = new Available(customer);
                        getSender().tell(message, getSelf());
                        reserve(msg.getNumberofseats(), msg.getCustomer());
                    }else{
                        System.out.println("I don't have any space for you");
                        ActorRef customer = msg.getCustomer();
                        NotAvailable message = new NotAvailable(customer);
                        getSender().tell(message, getSelf());
                    }
                })
                .match(Buy.class, msg -> {
                    System.out.println("Allright the seats are reserved");
                    Reservation message = new Reservation(getReservation(msg.getCustomer()) ,msg.getCustomer());
                    getSender().tell(message, getSelf());
                })
                .match(Cancel.class, msg -> {
                    System.out.println("Allright I'll remove the reservation");
                    cancelReservation(msg.getCustomer());
                })
                .build();
    }

    public void preStart(){
        addSeats();
    }

    private void addSeats(){
        for (int i=0; i < 40; i++){
            Seat seat = new Seat(section,i+1);
            seats.add(seat);
        }
    }

    private void reserve(int n, ActorRef newOwner) {
        for (int i = 0; i < n;i++){
            seats.get(i).setOwner(newOwner);
            takenseats.add(seats.get(i));
            seats.remove(i);
        }
    }

    private ArrayList<String> getReservation( ActorRef owner){
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 0; i < takenseats.size(); i++){
            if (takenseats.get(i).getOwner() == owner){
                String ticket = "Sectionnumber "+ takenseats.get(i).getSection() + " and Seatnumber " + takenseats.get(i).getSeatNumber();
                temp.add(ticket);
            }
        }
        return temp;
    }

    private void cancelReservation(ActorRef owner){
        for (int i = 0; i < takenseats.size(); i++){
            if (takenseats.get(i).getOwner().equals(owner)){
                takenseats.get(i).setOwner(null);
                seats.add(takenseats.get(i));
                takenseats.remove(i);
                i--;
            }
        }
    }
}

