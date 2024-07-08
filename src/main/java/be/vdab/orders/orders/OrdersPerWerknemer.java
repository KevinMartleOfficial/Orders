package be.vdab.orders.orders;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrdersPerWerknemer(int id, String omschrijving, BigDecimal bedrag, LocalDateTime goedgekeurd) {
    OrdersPerWerknemer(Order order){
        this(order.getId(), order.getOmschrijving(), order.getBedrag(), order.getGoedgekeurd());

    }
}



