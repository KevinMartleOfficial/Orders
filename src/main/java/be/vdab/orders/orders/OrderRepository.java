package be.vdab.orders.orders;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepository {
    private final JdbcClient jdbcClient;

    public OrderRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public void voegOrderToe(Order order){
        String sql = """
                insert into orders(werknemerId, omschrijving, bedrag, goedgekeurd)
                values
                (?, ?, ?, ?)
                """;
        jdbcClient.sql(sql)
                .params(order.getWerknemerId(), order.getOmschrijving(), order.getBedrag(), order.getGoedgekeurd())
                .update();

    }

    public List<OrdersPerWerknemer> findOrdersByWerknemerId(int id){
        String sql = """
                select id, werknemerId, omschrijving, bedrag, goedgekeurd
                from orders
                where werknemerId = ?
                order by id
                """;
        return jdbcClient.sql(sql)
                .param(id)
                .query(Order.class)
                .stream()
                .map(OrdersPerWerknemer::new)
                .toList();
    }

    public Optional<Integer> findOrderById(int id){
        String sql = """
                select id
                from orders
                where id = ?
                for update
                """;
        return jdbcClient.sql(sql)
                .param(id)
                .query(Integer.class)
                .optional();
    }

    public Optional<Order> zoekOrderVanWerknemerVanChef(int chefId, int orderId){
        String sql = """
                select orders.id as id, orders.werknemerId as werknemerId, orders.omschrijving as omschrijving, orders.bedrag as bedrag, orders.goedgekeurd as goedgekeurd
                from orders inner join werknemers
                on orders.werknemerId = werknemers.id
                where chefId = ? and orders.id = ?
                """;

        return jdbcClient.sql(sql)
                .params(chefId, orderId)
                .query(Order.class)
                .optional();
    }

    public void update(Order order){
        String sql = """
                update orders
                set goedgekeurd = ?
                where id =?
                """;
        jdbcClient.sql(sql)
                .params(order.getGoedgekeurd(), order.getId())
                .update();
    }
}
