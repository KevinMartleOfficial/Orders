package be.vdab.orders.orders;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class WerknemerRepository {
    private final JdbcClient jdbcClient;

    public WerknemerRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Optional<Werknemer> findById(int id){
        String sql = """
                select id, chefId, voornaam, familienaam
                from werknemers
                where id = ?
                """;
        return jdbcClient.sql(sql)
                .param(id)
                .query(Werknemer.class)
                .optional();
    }




}
