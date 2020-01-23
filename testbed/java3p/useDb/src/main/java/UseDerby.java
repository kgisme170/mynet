import com.sun.rowset.JdbcRowSetImpl;

import javax.sql.rowset.*;
import javax.sql.rowset.spi.SyncProviderException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

class ConnectDerby {
    private ResultSet resultSet = null;
    private Statement statement = null;
    private Properties properties = null;
    private Connection connection = null;
    private RowSetFactory factory = null;

    public ConnectDerby() {
        try {
            factory = RowSetProvider.newFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void iterateRs() {
        System.out.println("----------------");
        try {
            while (resultSet.next()) {
                StringBuilder builder = new StringBuilder(resultSet.getString(1));
                builder.append("\t");
                builder.append(resultSet.getInt(2));
                System.out.println(builder.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("----------------");
    }

    public void iterateCachedRs() {
        try {
            CachedRowSet rowSet = factory.createCachedRowSet();
            rowSet.populate(resultSet);

            while (rowSet.next()) {
                System.out.println("id:" + rowSet.getString(1));
            }

            System.out.println("----------------");

            rowSet.absolute(2);
            rowSet.updateString("name", "my");
            System.out.println(rowSet.getString(1));
            rowSet.updateRow();
            rowSet.moveToInsertRow();

            rowSet.updateString("name", "you");
            rowSet.updateInt("age", 33);
            rowSet.insertRow();
            System.out.println("----------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private final String user = "myuser";
    private final String password = "mypassword";
    private final String url = "org.apache.derby.jdbc.EmbeddedDriver";

    public void init() {
        try {
            Class.forName(url).newInstance();
            properties = new Properties();
            properties.put("user", user);
            properties.put("password", password);

            connection = DriverManager.getConnection("jdbc:derby:hello;create=true", properties);
            connection.setAutoCommit(false);

            statement = connection.createStatement();
            statement.execute("create table t(name varchar(40), age int)");
            statement.execute("insert into t values('ab', 26)");
            statement.execute("insert into t values ('xyz', 32)");
            resultSet = statement.executeQuery("SELECT name, age FROM t ORDER BY age");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        try {
            statement.execute("drop table t");

            resultSet.close();
            statement.close();
            connection.commit();
            connection.close();
            try {
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
            } catch (SQLException se) {
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void testUpdate() {
        try {
            CachedRowSet rowSet = factory.createCachedRowSet();
            rowSet.populate(resultSet);

            if (!rowSet.absolute(1)) {
                return;
            }

            rowSet.updateString("name", "him");
            rowSet.updateRow();
            try {
                rowSet.acceptChanges(connection);
            } catch (SyncProviderException ex) {
                System.out.println("Error commiting changes to the database: " + ex);
            }

            while (rowSet.next()) {
                System.out.println("id:" + rowSet.getString(1));
            }

            iterateRs();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void useJdbcRowSet() {
        try {

            JdbcRowSet jdbcRS = new JdbcRowSetImpl(connection);
            System.out.println(jdbcRS);
            jdbcRS.setType(ResultSet.TYPE_SCROLL_INSENSITIVE);
            String sql = "SELECT * FROM t";
            jdbcRS.setCommand(sql);
            jdbcRS.execute();
            while (jdbcRS.next()) {
                System.out.println("name = " + jdbcRS.getString(1));
                System.out.println("age = " + jdbcRS.getInt(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/**
 * @author liming.gong
 */

public class UseDerby {
    public static void main(String[] args) {
        if (args.length > 1) {
            ConnectDerby connectDerby = new ConnectDerby();
            connectDerby.init();
            connectDerby.iterateCachedRs();
            connectDerby.testUpdate();
            connectDerby.destroy();
        } else {
            ConnectDerby connectDerby = new ConnectDerby();
            connectDerby.init();
            connectDerby.useJdbcRowSet();
            connectDerby.destroy();
        }
    }
}
