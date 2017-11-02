import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class World {

    public static void main(String[] args) throws Exception{
        ActorSystem system = ActorSystem.create("Ziggodome");
        ActorRef ticketbureau = system.actorOf(Props.create(TicketBureau.class), "Ticketbureau");
        ticketbureau.tell("Start", ActorRef.noSender());
        Thread.sleep(2000);
        ActorRef customer1 = system.actorOf(Customer.prop(ticketbureau), "Customer1");
        Thread.sleep(2000);
        system.terminate();
    }
}
