package messages;

import akka.actor.ActorRef;

public final class NotAvailable {
    private ActorRef customer;

    public NotAvailable(ActorRef customer) {
        this.customer = customer;
    }

    public ActorRef getCustomer(){
        return customer;
    }
}
