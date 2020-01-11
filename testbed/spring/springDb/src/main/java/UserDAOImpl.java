import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
/**
 * @author liming.gong
 */
public class UserDAOImpl extends JdbcDaoSupport implements IUserDAO {
    @Override
    public void addUser(User user) {
        String sql = "insert into t3 values(?,?,?)";
        this.getJdbcTemplate().update(sql, user.getId(), user.getUsername(), user.getPassword());
    }
    @Override
    public void deleteUser(int id) {
        String sql = "delete from t3 where id=?";
        this.getJdbcTemplate().update(sql, id);
    }
    @Override
    public void updateUser(User user) {
        String sql = "update t3 set username=?,password=? where id=?";
        this.getJdbcTemplate().update(sql, user.getUsername(), user.getPassword(), user.getId());
    }
    @Override
    public String searchUserName(int id) {
        String sql = "select username from t3 where id=?";
        return this.getJdbcTemplate().queryForObject(sql, String.class, id);
    }
    @Override
    public List<User> findAll() {
        String sql = "select * from t3";
        return this.getJdbcTemplate().query(sql, new UserRowMapper());
    }
    @Override
    public User searchUser(int id) {
        String sql = "select * from t3 where id=?";
        return this.getJdbcTemplate().queryForObject(sql, new UserRowMapper(), id);
    }

    class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    }
}
