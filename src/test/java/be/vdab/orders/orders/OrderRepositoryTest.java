package be.vdab.orders.orders;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(OrderRepository.class)
@Sql({"/werknemers.sql", "/orders.sql"})
class OrderRepositoryTest {
    private static final String WERKNEMERS_TABLE = "werknemers";
    private static final String ORDERS_TABLE = "orders";
    private final OrderRepository orderRepository;
    private final JdbcClient jdbcClient;

    public OrderRepositoryTest(OrderRepository orderRepository, JdbcClient jdbcClient) {
        this.orderRepository = orderRepository;
        this.jdbcClient = jdbcClient;
    }

    private int idVanEenTestWerknemer(){
        return jdbcClient.sql("select id from werknemers where voornaam = 'Jos'")
                .query(Integer.class)
                .single();
    }

    @Test
    void voegOrderToeVoegtEenOrderToe(){
        int werknemerId = idVanEenTestWerknemer();
        orderRepository.voegOrderToe(new Order(0, werknemerId, "test", BigDecimal.TEN, null));
        int aantalRecords = JdbcTestUtils.countRowsInTableWhere(jdbcClient, ORDERS_TABLE, "werknemerId = " + werknemerId + " and omschrijving = 'test' and bedrag = 10 and goedgekeurd is null" );
        assertThat(aantalRecords).isOne();
    }

    @Test
    void findOrdersByWerknemerIdVindtDeJuisteOrders(){
        int werknemersId = idVanEenTestWerknemer();
        int aantalRecords = JdbcTestUtils.countRowsInTableWhere(jdbcClient, ORDERS_TABLE, "werknemerId = " + werknemersId);
        assertThat(orderRepository.findOrdersByWerknemerId(werknemersId))
                .hasSize(2)
                .extracting(OrdersPerWerknemer::getClass)
                .isSorted();








    }

}