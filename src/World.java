import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class World {

    public static void main(String[] args) throws Exception{
        ActorSystem system = ActorSystem.create("Ziggodome");
        ActorRef ticketseller = system.actorOf(Props.create(TicketBureau.class), "Ticketseller");
        ticketseller.tell("Start", ActorRef.noSender());
        Thread.sleep(2000);
        ActorRef customer = system.actorOf(Customer.prop(ticketseller), "Customer");
        Thread.sleep(2000);
        system.terminate();
    }
}
