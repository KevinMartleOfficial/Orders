package be.vdab.orders.orders;

public class OrderNietGevondenException extends RuntimeException{
    private final int id;

    public OrderNietGevondenException(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
