import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class World {

    public static void main(String[] args) throws Exception{
        ActorSystem system = ActorSystem.create("Ziggodome");

        ActorRef seatmanager = system.actorOf(Props.create(SeatManager.class), "Seatmanager");
        ActorRef ticketseller = system.actorOf(Props.create(TicketSeller.class), "Ticketseller");
        ActorRef customer = system.actorOf(Props.create(Customer.class), "Customer");


        Thread.sleep(1000);
        system.terminate();
    }
}
