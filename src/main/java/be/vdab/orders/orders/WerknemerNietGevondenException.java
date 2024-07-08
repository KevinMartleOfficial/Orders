package be.vdab.orders.orders;

public class WerknemerNietGevondenException extends RuntimeException{
    private final int id;

    public WerknemerNietGevondenException(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

}
