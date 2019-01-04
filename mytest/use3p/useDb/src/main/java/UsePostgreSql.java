import com.sun.rowset.CachedRowSetImpl;
import com.sun.rowset.JdbcRowSetImpl;
import com.sun.rowset.WebRowSetImpl;

import javax.sql.RowSet;
import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;
import javax.sql.rowset.*;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liming.gong
 */
class MyRowSetListener implements RowSetListener
{
    @Override
    public void cursorMoved(RowSetEvent event)
    {
        System.out.println("-->cursorMoved");
    }
    @Override
    public void rowChanged(RowSetEvent event)
    {
        System.out.println("-->rowChanged");
    }
    @Override
    public void rowSetChanged(RowSetEvent event)
    {
        System.out.println("-->rowSetChanged");
    }
}

class FilterExample implements Predicate {
    private Pattern pattern;

    public FilterExample(String regexQuery) {
        if (regexQuery != null && !regexQuery.isEmpty()) {
            pattern = Pattern.compile(regexQuery);
        }
    }

    @Override
    public boolean evaluate(RowSet rs) {
        try {
            if (!rs.isAfterLast()) {
                String name = rs.getString("name");
                System.out.println(String.format(
                        "Searching for pattern '%s' in %s", pattern.toString(),
                        name));
                Matcher matcher = pattern.matcher(name);
                return matcher.matches();
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean evaluate(Object value, int column) throws SQLException {
        return false;
    }

    @Override
    public boolean evaluate(Object value, String columnName) throws SQLException {
        return false;
    }
}

/**
 * @author liming.gong
 */
public class UsePostgreSql {
    private static final String url = "jdbc:postgresql://localhost:5432/postgres";
    private static final String user = "postgres";
    private static final String password = "password";
    private static final String string = "SELECT * FROM company";
    public static void jdbcRowSetWithListener() {
        try {
            // init
            Connection connection = DriverManager.getConnection(
                    url,
                    user,
                    password);
            connection.setAutoCommit(false);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT name, age FROM company ORDER BY age");
            System.out.println("----------------");
            while (resultSet.next()) {
                StringBuilder builder = new StringBuilder(resultSet.getString(1));
                builder.append("\t");
                builder.append(resultSet.getInt(2));
                System.out.println(builder.toString());
            }

            // JDBC 需要连接
            JdbcRowSet jdbcRS = new JdbcRowSetImpl(connection);
            System.out.println(jdbcRS);

            jdbcRS.setType(ResultSet.TYPE_SCROLL_INSENSITIVE);
            String sql = string;
            jdbcRS.setCommand(sql);
            jdbcRS.execute();
            jdbcRS.addRowSetListener(new MyRowSetListener());
            while (jdbcRS.next()) {
                // each call to next, generates a cursorMoved event
                System.out.println("name = " + jdbcRS.getString("name"));
                System.out.println("age = " + jdbcRS.getInt("age"));
            }

            // 过滤
            RowSetFactory rsf = RowSetProvider.newFactory();
            FilteredRowSet frs = rsf.createFilteredRowSet();
            frs.setCommand(string);
            frs.execute(connection);
            frs.setFilter(new FilterExample("^[A-C].*"));

            ResultSetMetaData resultSetMetaData = frs.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            while (frs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.println(
                            resultSetMetaData.getColumnLabel(i)
                                    + " = "
                                    + frs.getObject(i) + " ");
                }
            }

            // destroy
            resultSet.close();
            statement.close();
            System.out.println("关闭返回的 set 和 statement");
            connection.commit();
            connection.close();
            System.out.println("提交 transaction 并关闭连接");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public static void cachedRowSet() {
        try {
            CachedRowSet crs = new CachedRowSetImpl();
            crs.setUsername(user);
            crs.setPassword(password);
            crs.setUrl(url);
            crs.setCommand(string);
            crs.execute();
            while (crs.next()) {
                System.out.println("name = " + crs.getString("name"));
                System.out.println("age = " + crs.getInt("age"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void webRowSet() {
        try {
            WebRowSet wrs = new WebRowSetImpl();
            wrs.setUsername(user);
            wrs.setPassword(password);
            wrs.setUrl(url);
            wrs.setCommand(string);
            wrs.execute();
            FileOutputStream fileOutputStream = new FileOutputStream("customers.xml");
            wrs.writeXml(fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
            jdbcRowSetWithListener();
            cachedRowSet();
            webRowSet();
        } catch (Exception e) {
        }
    }
}