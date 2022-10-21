package study.board.Repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import study.board.Board;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static study.board.connection.ConnectionConst.*;

class BoardRepositoryTest {

    BoardRepository repository;
    @BeforeEach
    void beforeEach() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        repository = new BoardRepository(dataSource);

    }


    @AfterEach
    void afterEach() {
        repository.clearRepository();
    }

    @Test
    void save() {
        //given
        Board board = new Board("title2", "asasasa2");

        //when
        repository.save(board);

        //then

    }

    @Test
    void findById() {
        //given
        Board board = new Board("title", "asasasa2");
        repository.save(board);

        //when
        Board findBoard = repository.findById(board.getId());

        //then
        assertThat(findBoard).isEqualTo(board);
    }

    @Test
    void findAll()  {
        //given
        Board board1 = new Board("title1","asas");
        Board board2 = new Board("title2","asas");

        repository.save(board1);
        repository.save(board2);

        //when
        List<Board> result = repository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
        System.out.println(result);
    }

    @Test
    void update()  {
        //given
        Board board = new Board("title", "asasasa2");
        repository.save(board);
        Board updateForm = new Board("title2","new");

        //when
        repository.update(board.getId(),updateForm);

        //then
        Board updateBoard = repository.findById(board.getId());
        assertThat(updateBoard.getTitle()).isEqualTo(updateForm.getTitle());
        assertThat(updateBoard.getContents()).isEqualTo(updateForm.getContents());

    }

    @Test
    void delete() {
        //given
        Board board = new Board("title2", "asasasa2");
        repository.save(board);
        //when
        repository.delete(board.getId());

        //then
        assertThrows(NoSuchElementException.class,()->repository.findById(board.getId()));
    }

}