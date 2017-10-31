package messages;

import akka.actor.ActorRef;

public final class Bought {
    private int section;
    private int numberofseats;
    private ActorRef customer;

    public Bought(int section, int numberofseats, ActorRef customer){
        this.section = section;
        this.numberofseats = numberofseats;
        this.customer = customer;
    }

    public int getSection(){
        return section;
    }
    public int getNumberofseats(){
        return numberofseats;
    }
    public ActorRef getCustomer(){
        return customer;
    }

}
