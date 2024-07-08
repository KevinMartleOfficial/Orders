package be.vdab.orders;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class DataSourceTest {
    private final DataSource dataSource;

    DataSourceTest(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Test
    void testenDatasourceKanEenConnectionGeven() throws SQLException{
        try(Connection connection = dataSource.getConnection()){
            assertThat(connection.getCatalog()).isEqualTo("orders");
        }
    }
}
