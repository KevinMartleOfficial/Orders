package be.vdab.orders.orders;

import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Or;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    private int idVanEenTestWerknemer2(){
        return jdbcClient.sql("select id from werknemers where voornaam = 'Piet'")
                .query(Integer.class)
                .single();
    }

    private int idVanEenTestOrder(){
        return jdbcClient.sql("select id from orders where omschrijving = 'test1'")
                .query(Integer.class)
                .single();
    }
    private int idVanEenTestOrder2(){
        return jdbcClient.sql("select id from orders where omschrijving = 'test2'")
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
    void findOrdersByWerknemerIdVindtDeJuisteOrdersEnIsGesorteerd(){
        int werknemersId = idVanEenTestWerknemer();
        int aantalRecords = JdbcTestUtils.countRowsInTableWhere(jdbcClient, ORDERS_TABLE, "werknemerId = " + werknemersId);
        assertThat(orderRepository.findOrdersByWerknemerId(werknemersId))
                .hasSize(aantalRecords)
                .extracting(OrdersPerWerknemer::id)
                .isSorted();
    }

    @Test
    void findOrderByIdVindtDeJuisteOrderMetBestaandeId(){
        int orderId = idVanEenTestOrder();
        assertThat(orderRepository.findOrderById(orderId)).hasValueSatisfying(order->assertThat(order.getOmschrijving()).isEqualTo("test1"));
    }

    @Test
    void findOrderByIdVindtGeenOrderMetOnbestaandeId(){
        assertThat(orderRepository.findOrderById(Integer.MAX_VALUE)).isEmpty();
    }

    @Test
    void updateWijzigtEenOrder(){
        int orderId = idVanEenTestOrder();
        int werknemerId = idVanEenTestWerknemer();
        Order order = new Order(orderId, werknemerId, "test1", BigDecimal.valueOf(5), LocalDateTime.now() );
        orderRepository.update(order);
        int aantalAangepasteRecords = JdbcTestUtils.countRowsInTableWhere(jdbcClient, ORDERS_TABLE, "id = "+ orderId + " and werknemerId = " + idVanEenTestWerknemer() +  " and omschrijving = 'test1' and bedrag = 5 and goedgekeurd is not null");
        assertThat(aantalAangepasteRecords).isOne();
    }

    @Test
    void zoekOrderVanWerknemerVanChefMetBestaandeChefIdEnOrderIdGeeftEenOrderTerug(){
        int chefId =idVanEenTestWerknemer();
        int werknemerId = idVanEenTestWerknemer2();
        int orderId = idVanEenTestOrder2();

        assertTrue(orderRepository.zoekOrderVanWerknemerVanChef(chefId, orderId).isPresent());


      //new Order(orderId, werknemerId, 'test3', 7, null)
    }

}