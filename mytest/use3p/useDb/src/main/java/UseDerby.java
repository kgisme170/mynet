import com.sun.rowset.CachedRowSetImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author liming.gong
 */

class ConnectDerby {
    private ResultSet rs = null;
    private Statement s = null;
    private Properties props = null;
    private Connection conn = null;

    public ConnectDerby() {
    }

    public void iterateRs() {
        System.out.println("----------------");
        try {
            while (rs.next()) {
                StringBuilder builder = new StringBuilder(rs.getString(1));
                builder.append("\t");
                builder.append(rs.getInt(2));
                System.out.println(builder.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("----------------");
    }

    public void iterateCachedRs() {
        try {
            CachedRowSetImpl rowSet = new CachedRowSetImpl();
            rowSet.populate(rs);
            System.out.println("----------------");
            while (rowSet.next()) {
                System.out.println("id:" + rowSet.getString(1));
            }
            System.out.println("----------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            System.out.println("加载嵌入式驱动");
            props = new Properties();
            props.put("user", "用户1");
            props.put("password", "用户11");

            conn = DriverManager.getConnection("jdbc:derby:hello;create=true", props);
            System.out.println("创建数据库hello");
            conn.setAutoCommit(false);

            s = conn.createStatement();
            s.execute("create table t(name varchar(40), age int)");
            System.out.println("创建表 t");
            s.execute("insert into t values('张三', 26)");
            s.execute("insert into t values ('李四', 32)");
            rs = s.executeQuery("SELECT name, age FROM t ORDER BY age");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        try {
            s.execute("drop table t");
            System.out.println("删除表 t");

            rs.close();
            s.close();
            System.out.println("关闭返回的 set 和 statement");
            conn.commit();
            conn.close();
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
}

public class UseDerby {
    public static void main(String[] args) {
        ConnectDerby connectDerby = new ConnectDerby();
        connectDerby.init();
        connectDerby.iterateCachedRs();
        connectDerby.destroy();
    }
}