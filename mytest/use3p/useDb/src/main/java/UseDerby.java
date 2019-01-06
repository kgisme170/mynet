import com.sun.rowset.JdbcRowSetImpl;
import com.sun.rowset.WebRowSetImpl;

import javax.sql.rowset.*;
import javax.sql.rowset.spi.SyncProviderException;
import java.io.FileOutputStream;
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
            // CachedRowSetImpl rowSet = new CachedRowSetImpl();
            // 用factory的方式比上面一句更好
            CachedRowSet rowSet = factory.createCachedRowSet();
            rowSet.populate(resultSet);
            System.out.println("----------------");

            while (rowSet.next()) {
                System.out.println("id:" + rowSet.getString(1));
            }

            System.out.println("----------------");

            rowSet.absolute(2);
            rowSet.updateString("name", "王二");
            System.out.println(rowSet.getString(1));
            rowSet.updateRow();
            rowSet.moveToInsertRow();

            rowSet.updateString("name", "麻子");
            rowSet.updateInt("age", 33);
            rowSet.insertRow();
            System.out.println("----------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private final String user = "用户1";
    private final String password = "用户11";
    private final String url = "org.apache.derby.jdbc.EmbeddedDriver";

    public void init() {
        try {
            Class.forName(url).newInstance();
            System.out.println("加载嵌入式驱动");
            properties = new Properties();
            properties.put("user", user);
            properties.put("password", password);

            connection = DriverManager.getConnection("jdbc:derby:hello;create=true", properties);
            System.out.println("创建数据库hello");
            connection.setAutoCommit(false);

            statement = connection.createStatement();
            statement.execute("create table t(name varchar(40), age int)");
            System.out.println("创建表 t");
            statement.execute("insert into t values('张三', 26)");
            statement.execute("insert into t values ('李四', 32)");
            resultSet = statement.executeQuery("SELECT name, age FROM t ORDER BY age");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        try {
            statement.execute("drop table t");
            System.out.println("删除表 t");

            resultSet.close();
            statement.close();
            System.out.println("关闭返回的 set 和 statement");
            connection.commit();
            connection.close();
            System.out.println("提交 transaction 并关闭连接");
            try {
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
            } catch (SQLException se) {
                System.out.println("Database 正常关闭");
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

            rowSet.updateString("name", "王二");
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
                // each call to next, generates a cursorMoved event
                System.out.println("name = " + jdbcRS.getString(1));
                System.out.println("age = " + jdbcRS.getInt(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void useWebRowSet() {
            try {
            WebRowSet wrs = new WebRowSetImpl();
            wrs.setUsername(user);
            wrs.setPassword(password);
            wrs.setUrl("jdbc:derby:hello;");
            wrs.setCommand("SELECT * FROM t");
            wrs.execute();
            FileOutputStream fileOutputStream = new FileOutputStream("customers.xml");
            wrs.writeXml(fileOutputStream);
            fileOutputStream.close();
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
            // connectDerby.useWebRowSet();
            connectDerby.destroy();
        }
    }
}