import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;
/**
 * @author liming.glm
 */
public class MyDBCPDataSource {
    private static BasicDataSource getDBCPDataSource() {
        Properties properties = new Properties();
        try {
            properties.load(MyDBCPDataSource.class.getResourceAsStream("dbcp.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUsername(properties.getProperty("username"));
        dataSource.setPassword(properties.getProperty("password"));
        dataSource.setUrl(properties.getProperty("url"));
        dataSource.setDriverClassName(properties.getProperty("driverClassName"));
        // http://commons.apache.org/proper/commons-dbcp/configuration.html

        dataSource.setInitialSize(GenericObjectPool.DEFAULT_MIN_IDLE);
        dataSource.setMaxActive(GenericObjectPool.DEFAULT_MAX_ACTIVE);
        dataSource.setMaxIdle(GenericObjectPool.DEFAULT_MAX_ACTIVE);
        dataSource.setMinIdle(GenericObjectPool.DEFAULT_MIN_IDLE);
        dataSource.setMaxWait(GenericObjectPool.DEFAULT_MAX_WAIT);
        dataSource.setTestOnBorrow(GenericObjectPool.DEFAULT_TEST_ON_BORROW);
        dataSource.setTestOnReturn(GenericObjectPool.DEFAULT_TEST_ON_RETURN);
        dataSource.setTestWhileIdle(GenericObjectPool.DEFAULT_TEST_WHILE_IDLE);
        return dataSource;
    }

    public static void main(String[] args) {
        BasicDataSource dataSource = MyDBCPDataSource.getDBCPDataSource();
        System.out.println("连接池的容量" + dataSource.getNumActive());
        Connection conn = null;
        Statement sm = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            sm = conn.createStatement();
            rs = sm.executeQuery("SELECT * FROM t_user");
            while (rs.next()) {
                long id = rs.getLong(1);
                String username = rs.getString(2);
                System.out.println("得到一条记录的值" + id + ":" + username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                sm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                System.out.println("连接池的容量" + dataSource.getNumActive());
                conn.close();
                System.out.println("连接池的容量" + dataSource.getNumActive());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}