package study.board.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.board.Board;
import study.board.Repository.BoardRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository repository;

    public void write(Board board) throws SQLException {
        repository.save(board);
    }

    public List<Board> boardList() throws SQLException {
        List list = new ArrayList();
     try {
         list = repository.findAll();
     } catch (NoSuchElementException e) {
         System.out.println("게시판에 글이 없습니다.");
     }
        return list;
    }

    public Board boardView(int id) throws SQLException {
        return repository.findById(id);

    }

    public void boardDelete(int id) {
        repository.delete(id);
    }

    public void boardUpdate(int id, Board board) throws SQLException {
        repository.update(id, board);
    }

}
