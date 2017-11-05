import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import messages.*;

import java.util.ArrayList;

public class SeatManager extends AbstractActor {
    private int testseats = 40;

    private int section;
    private ArrayList<Seat> seats = new ArrayList<>();
    private ArrayList<Seat> takenseats = new ArrayList<>();

    private SeatManager(int section) {
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
                        Available message = new Available(msg.getCustomer());
                        getSender().tell(message, getSelf());
                        reserve(msg.getNumberofseats(), msg.getCustomer());
                    }else{
                        System.out.println("I don't have any space for you");
                        NotAvailable message = new NotAvailable(msg.getCustomer());
                        getSender().tell(message, getSelf());
                    }
                })
                .match(Buy.class, msg -> {
                    System.out.println("Allright the seats " + msg.getNumberofseats() + " in section " + msg.getSection() + " are reserved");
                    Reservation message = new Reservation(getReservation(msg.getCustomer()) ,msg.getCustomer());
                    getSender().tell(message, getSelf());
                })
                .match(Cancel.class, msg -> {
                    System.out.println("Allright I'll remove the reservation in section " + msg.getSection() + " of " + msg.getNumberofseats() + " seats");
                    cancelReservation(msg.getCustomer(),msg.getNumberofseats());
                })
                .matchEquals("Print", s ->{
                    printFreeSeats();
                })
                .matchAny(object ->{
                    System.out.println("Wrong Message");
                })
                .build();
    }

    public void preStart(){
        addSeats();
    }



    public void postStop() throws Exception {
        printFreeSeats();
    }

    private void addSeats(){
        for (int i=0; i < testseats; i++){
            Seat seat = new Seat(section,i+1);
            seats.add(seat);
        }
    }

    private void reserve(int n, ActorRef newOwner) {
        for (int i = 0; i < n; i++){
            seats.get(i).setOwner(newOwner);
            takenseats.add(seats.get(i));
        }
        for (int i = 0; i < n; i++){
            seats.remove(0);
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

    private void cancelReservation(ActorRef owner, int nmbrseats) {
        int seatsremoved = 0;
        for (int i = 0; i < takenseats.size(); i++) {
            if (nmbrseats > seatsremoved) {
                if (takenseats.get(i).getOwner().equals(owner)) {
                    takenseats.get(i).setOwner(null);
                    seats.add(takenseats.get(i));
                    takenseats.remove(i);
                    i--;
                    seatsremoved++;
                }
            }
        }
        System.out.println(seatsremoved + " succesfully removed from section " + section);
    }

    public void printFreeSeats(){
        System.out.println("Section " + section + " has " + seats.size() + " left");
    }
}

