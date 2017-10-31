package messages;

import akka.actor.ActorRef;

public final class IsAvailable {
    private int numberofseats;
    private ActorRef customer;

    public IsAvailable(int numberofseats, ActorRef customer){
        this.numberofseats = numberofseats;
        this.customer = customer;
    }

    public int getNumberofseats(){
        return numberofseats;
    }

    public ActorRef getCustomer(){
        return customer;
    }
}
