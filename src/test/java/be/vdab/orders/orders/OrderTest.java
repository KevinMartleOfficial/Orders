package be.vdab.orders.orders;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void orderIsCorrect(){
        new Order(0, 2, "test", BigDecimal.TEN, null);
    }

    @Test
    void eenOmschrijvingKanGeenLegeStringBevatten(){
        assertThatIllegalArgumentException().isThrownBy(()->new Order(0, 2, "", BigDecimal.TEN, LocalDateTime.now()));
    }

    @Test
    void eenBedragKanNietNegatiefZijn(){
        assertThatIllegalArgumentException().isThrownBy(()-> new Order(0, 2, "test", BigDecimal.valueOf(-5), LocalDateTime.now()));
    }

    @Test
    void eenBedragKanNietNulZijn(){
        assertThatIllegalArgumentException().isThrownBy(()-> new Order(0, 2, "test" , BigDecimal.ZERO, LocalDateTime.now()));
    }

    @Test
    void methodeUpdateGoedgekeurdPastDeGoedgekeurdAan(){
        Order order = new Order(0, 2, "test", BigDecimal.TEN, null);
        order.updateGoedgekeurd();
        assertThat(order.getGoedgekeurd()).isNotNull();
    }

}