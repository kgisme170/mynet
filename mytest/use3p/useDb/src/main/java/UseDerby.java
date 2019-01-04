import com.sun.rowset.CachedRowSetImpl;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
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

    public void init() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            System.out.println("加载嵌入式驱动");
            properties = new Properties();
            properties.put("user", "用户1");
            properties.put("password", "用户11");

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
            //rowSet.setTableName("myTable");
            rowSet.populate(resultSet);

            if (!rowSet.absolute(1)) {
                return;
            }

            // rowSet.deleteRow()
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
}

/**
 * @author liming.gong
 */

public class UseDerby {
    public static void main(String[] args) {
        ConnectDerby connectDerby = new ConnectDerby();
        connectDerby.init();
        /**
         * 第一个test connectDerby.iterateCachedRs
         * 第一个test connectDerby.destroy
         */

        connectDerby.testUpdate();
        connectDerby.destroy();
    }
}