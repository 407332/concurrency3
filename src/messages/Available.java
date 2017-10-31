package messages;

import akka.actor.ActorRef;

public final class Available {
    private ActorRef customer;

    public Available(ActorRef customer) {
        this.customer = customer;
    }

    public ActorRef getCustomer(){
        return customer;
    }
}
