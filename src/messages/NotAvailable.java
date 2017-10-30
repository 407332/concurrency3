package messages;

import akka.actor.ActorRef;

public final class NotAvailable {
    ActorRef customer;

    public NotAvailable(ActorRef customer) {
        this.customer = customer;
    }

    public ActorRef getCustomer(){
        ActorRef var = customer;
        return var;
    }
}
