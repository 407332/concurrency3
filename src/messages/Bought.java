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
        int var = section;
        return var;
    }
    public int getNumberofseats(){
        int var = numberofseats;
        return var;
    }
    public ActorRef getCustomer(){
        ActorRef var = customer;
        return var;
    }

}
