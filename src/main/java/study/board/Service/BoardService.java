package study.board.Service;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.board.Board;
import study.board.Repository.BoardRepository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static study.board.connection.ConnectionConst.*;

@Service
public class BoardService {

    BoardRepository repository = new BoardRepository(hikari());


    public DataSource hikari() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        return dataSource;
    }

    public void write(Board board)  {
        repository.save(board);
    }

    public List<Board> boardList() {
        List list = new ArrayList();
     try {
         list = repository.findAll();
     } catch (NoSuchElementException e) {
         System.out.println("게시판에 글이 없습니다.");
     }
        return list;
    }

    public Board boardView(int id){
        return repository.findById(id);

    }

    public void boardDelete(int id) {
        repository.delete(id);
    }

    public void boardUpdate(int id, Board board) {
        repository.update(id, board);
    }

}
