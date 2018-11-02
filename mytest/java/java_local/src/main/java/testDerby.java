import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class testDerby {
    public static void main(String[] args) {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            System.out.println("加载嵌入式驱动");
            Properties props = new Properties();
            props.put("user", "用户1");
            props.put("password", "用户11");
            //create and connect the database named helloDB
            Connection conn = DriverManager.getConnection("jdbc:derby:hello;create=true", props);
            System.out.println("创建数据库hello");
            conn.setAutoCommit(false);

            // create a table and insert two records
            Statement s = conn.createStatement();
            s.execute("create table t(name varchar(40), age int)");
            System.out.println("创建表 t");
            s.execute("insert into t values('张三', 26)");
            s.execute("insert into t values ('李四', 32)");
            // list the two records
            ResultSet rs = s.executeQuery(
                    "SELECT name, age FROM t ORDER BY age");
            System.out.println("----------------");
            while (rs.next()) {
                StringBuilder builder = new StringBuilder(rs.getString(1));
                builder.append("t");
                builder.append(rs.getInt(2));
                System.out.println(builder.toString());
            }
            // delete the table
            s.execute("drop table t");
            System.out.println("删除表 t");

            rs.close();
            s.close();
            System.out.println("关闭返回的 set 和 statement");
            conn.commit();
            conn.close();
            System.out.println("提交 transaction 并关闭连接");

            try { // perform. a clean shutdown
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
            } catch (SQLException se) {
                System.out.println("Database 正常关闭");
            }
        } catch (Throwable e) {
            // handle the exception
            e.printStackTrace();
        }
        System.out.println("SimpleApp finished");
    }
}