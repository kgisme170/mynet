import com.sun.rowset.WebRowSetImpl;

import javax.sql.rowset.WebRowSet;
import java.io.FileOutputStream;
import java.sql.DriverManager;
import java.util.Properties;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author liming.gong
 */
public class UserDerby2 {
    public static void main(String[] args) {
        final String user = "user";
        final String password = "password";
        final String url = "org.apache.derby.jdbc.EmbeddedDriver";

        try { // create
            Class.forName(url).newInstance();
            Properties properties = new Properties();
            properties.put("user", user);
            properties.put("password", password);

            Connection connection = DriverManager.getConnection("jdbc:derby:hello;create=true", properties);
            connection.setAutoCommit(false);

            Statement statement = connection.createStatement();
            statement.execute("create table t(name varchar(40), age int)");
            statement.execute("insert into t values('John', 26)");
            statement.execute("insert into t values ('Smith', 32)");
            ResultSet resultSet = statement.executeQuery("SELECT name, age FROM t ORDER BY age");
            while (resultSet.next()) {
                System.out.println("id:" + resultSet.getString(1));
            }
            // experiment begins
            try {
                WebRowSet wrs = new WebRowSetImpl();
                wrs.setUsername(user);
                wrs.setPassword(password);
                wrs.setUrl("jdbc:derby:hello;");
                wrs.setCommand("SELECT * FROM t");
                System.out.println("step1");
                wrs.execute();
                System.out.println("step2");
                FileOutputStream fileOutputStream = new FileOutputStream("customers.xml");
                wrs.writeXml(fileOutputStream);
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //experiment ends
            statement.execute("drop table t");
            resultSet.close();
            statement.close();
            connection.commit();
            connection.close();
            try {
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
            } catch (SQLException se) {
                System.out.println("Database shutdown with exception");
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }
}