package be.vdab.orders.orders;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class Order {
    private final int id;
    private final int werknemerId;
    private final String omschrijving;
    private final BigDecimal bedrag;
    private LocalDateTime goedgekeurd;

    public Order(int id, int werknemerId, String omschrijving, BigDecimal bedrag, LocalDateTime goedgekeurd) {
        if(omschrijving.isEmpty()){
            throw new IllegalArgumentException("Omschrijving kan niet leeg zijn");
        }
        if(bedrag.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Bedrag kan niet negatief zijn");
        }


        this.id = id;
        this.werknemerId = werknemerId;
        this.omschrijving = omschrijving;
        this.bedrag = bedrag;
        this.goedgekeurd = goedgekeurd;
    }

    public void updateGoedgekeurd(){
       goedgekeurd = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public int getWerknemerId() {
        return werknemerId;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public BigDecimal getBedrag() {
        return bedrag;
    }

    public LocalDateTime getGoedgekeurd() {
        return goedgekeurd;
    }
}
