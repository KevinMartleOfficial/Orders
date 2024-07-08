package be.vdab.orders.orders;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@Import(WerknemerRepository.class)
@Sql("/werknemers.sql")
class WerknemerRepositoryTest {
    private final WerknemerRepository werknemerRepository;
    private final JdbcClient jdbcClient;

    public WerknemerRepositoryTest(WerknemerRepository werknemerRepository, JdbcClient jdbcClient) {
        this.werknemerRepository = werknemerRepository;
        this.jdbcClient = jdbcClient;
    }

    private int idVanTestWerknemer(){
        return jdbcClient.sql("select id from werknemers where voornaam = 'Jos'")
                .query(Integer.class)
                .single();
    }

    @Test
    void findByIdVindtEenWerknemerMetBestaandeId(){
        int id = idVanTestWerknemer();
        assertThat(werknemerRepository.findById(id)).hasValueSatisfying(werknemer -> assertThat(werknemer.getVoornaam()).isEqualTo("Jos"));
    }

    @Test
    void findByIdMetOnbestaandeIdVindtGeenWerknemer(){
        assertThat(werknemerRepository.findById(Integer.MAX_VALUE)).isEmpty();
    }
}