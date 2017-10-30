import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import scala.collection.script.Start;

public class World {

    public static void main(String[] args) throws Exception{
        ActorSystem system = ActorSystem.create("Ziggodome");
        ActorRef ticketseller = system.actorOf(Props.create(TicketSeller.class), "Ticketseller");
        ActorRef customer = system.actorOf(Props.create(Customer.class), "Customer");

        ticketseller.tell("Start", ActorRef.noSender());
        Thread.sleep(2000);
        system.terminate();
    }
}
