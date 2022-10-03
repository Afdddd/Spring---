package study.board.connection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DBConnectionUtilTest {

    @Test
    void getConnection() {

        Connection connection = DBConnectionUtil.getConnection();
        assertThat(connection).isNotNull();
    }
}