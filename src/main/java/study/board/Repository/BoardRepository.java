package study.board.Repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import study.board.Board;

import javax.sql.DataSource;
import java.util.*;

@Slf4j
@Repository
public class BoardRepository {

    private static int sequence = 0;

    private final JdbcTemplate jdbcTemplate;

    public BoardRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Board save(Board board) {
        String sql = "insert into BOARD(id,title,contents) values(?,?,?)";
        board.setId(++sequence);
        jdbcTemplate.update(sql,board.getId(),board.getTitle(),board.getContents());
        return board;
    }

    public Board findById(int id) {
        String sql = "select * from BOARD where id =?";
        return jdbcTemplate.queryForObject(sql, BoardRowMapper(), id);

    }

    private RowMapper<Board> BoardRowMapper() {
        return (rs, rowNum) -> {
            Board board = new Board();
            board.setId(rs.getInt("ID"));
            board.setTitle(rs.getString("TITLE"));
            board.setContents(rs.getString("CONTENTS"));
            return board;
        };

    }

    public List<Board> findAll() {
        String sql = "select * from board";
        return jdbcTemplate.query(sql,BoardRowMapper());
    }

//    private List<Board> getList(ResultSet rs) throws SQLException {
//        List list = new ArrayList<>();
//
//        try{
//            if (rs.next()) {
//                do {
//                    Map map = new HashMap();
//
//                    ResultSetMetaData rsMd = rs.getMetaData();
//                    int rsMdCo = rsMd.getColumnCount();
//                    for (int i = 1; i <= rsMdCo; i++) {
//                        String column = rsMd.getColumnName(i).toLowerCase();
//                        String value = rs.getString(column);
//
//                        map.put(column, value);
//                    }
//                    list.add(map);
//                } while (rs.next());
//            } else {
//                throw new NoSuchElementException("board not found");
//            }
//        } catch (SQLException e) {
//            log.error("DB error", e);
//            throw e;
//        }
//        return list;
//
//    }

    public void update(int id,Board board) {
        String sql = "update Board set title=?, contents=? where id=?";
        jdbcTemplate.update(sql, board.getTitle(), board.getContents(), id);
    }

    public void delete(int id) {
        String sql = "delete from board where id=?";
        jdbcTemplate.update(sql, id);
    }

    public void clearRepository() {
        String sql = "delete from board";
        jdbcTemplate.update(sql);
    }

}
