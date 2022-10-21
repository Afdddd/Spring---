package study.board.Repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.stereotype.Repository;
import study.board.Board;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Slf4j
@Repository
public class BoardRepository {

    private static int sequence = 0;

    @Autowired
    private final DataSource dataSource;
    private final SQLExceptionTranslator exTranslator;

    public BoardRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        this.exTranslator = new SQLErrorCodeSQLExceptionTranslator();
    }

    public Board save(Board board) {
        String sql = "insert into BOARD(id,title,contents) values(?,?,?)";
        board.setId(++sequence);

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,board.getId());
            pstmt.setString(2,board.getTitle());
            pstmt.setString(3,board.getContents());
            pstmt.executeUpdate();
            return board;
        } catch (SQLException e) {
            throw exTranslator.translate("save", sql, e);
        }
        finally {
            close(con, pstmt, rs);
        }
    }

    public Board findById(int id) {
        String sql = "select * from BOARD where id =?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                Board board = new Board();
                board.setId(rs.getInt("id"));
                board.setTitle(rs.getString("title"));
                board.setContents(rs.getString("contents"));
                return board;
            } else {
                throw new NoSuchElementException("board not found id=" + id);
            }

        }catch (SQLException e){
            throw exTranslator.translate("findById", sql, e);
        }
        finally {
            close(con,pstmt,rs);
        }
    }

    public List<Board> findAll() {
        String sql = "select * from board";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Board> list = getList(rs);

            return list;
        } catch (SQLException e) {
            throw exTranslator.translate("findAll", sql, e);
        }
    }

    private List<Board> getList(ResultSet rs) throws SQLException {
        List list = new ArrayList<>();

        try{
            if (rs.next()) {
                do {
                    Map map = new HashMap();

                    ResultSetMetaData rsMd = rs.getMetaData();
                    int rsMdCo = rsMd.getColumnCount();
                    for (int i = 1; i <= rsMdCo; i++) {
                        String column = rsMd.getColumnName(i).toLowerCase();
                        String value = rs.getString(column);

                        map.put(column, value);
                    }
                    list.add(map);
                } while (rs.next());
            } else {
                throw new NoSuchElementException("board not found");
            }
        } catch (SQLException e) {
            log.error("DB error", e);
            throw e;
        }
        return list;

    }

    public void update(int id,Board board) {
        String sql = "update Board set title=?, contents=? where id=?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, board.getTitle());
            pstmt.setString(2, board.getContents());
            pstmt.setInt(3, id);
            int resultSize = pstmt.executeUpdate();
            log.info("resultSize={}",resultSize);
        } catch (SQLException e) {
            throw exTranslator.translate("update", sql, e);
        }finally {
            close(con, pstmt, rs);
        }
    }

    public void delete(int id) {
        String sql = "delete from board where id=?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw exTranslator.translate("delete", sql, e);
        }finally {
            close(con,pstmt,null);
        }
    }

    public void clearRepository() {
        String sql = "delete from board";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw exTranslator.translate("clear", sql, e);
        }
    }

    private void close(Connection con, Statement stmt, ResultSet rs) {

        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(con);


    }

    private Connection getConnection() throws SQLException {

        Connection connection = dataSource.getConnection();
        log.info("connection={} class={}",connection,connection.getClass());
        return connection;
    }
}
