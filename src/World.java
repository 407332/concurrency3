import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class World {

    public static void main(String[] args) throws Exception{
        ActorSystem system = ActorSystem.create("Ziggodome");
        ActorRef ticketbureau = system.actorOf(Props.create(TicketBureau.class), "Ticketbureau");
        Thread.sleep(1000);
        ActorRef customer1 = system.actorOf(Customer.prop(ticketbureau), "Customer1");
        ActorRef customer2 = system.actorOf(Customer.prop(ticketbureau), "Customer2");
        ActorRef customer3 = system.actorOf(Customer.prop(ticketbureau), "Customer3");
        ActorRef customer4 = system.actorOf(Customer.prop(ticketbureau), "Customer4");
        ActorRef customer5 = system.actorOf(Customer.prop(ticketbureau), "Customer5");
        Thread.sleep(1000);
        system.terminate();
    }
}
