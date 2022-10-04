package study.board.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.board.Board;
import study.board.Repository.BoardRepository;

import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository repository;

    public void write(Board board) throws SQLException {
        repository.save(board);
    }

    public List<Board> boardList() throws SQLException {
      return repository.findAll();
    }

}
