import com.sun.rowset.JdbcRowSetImpl;

import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;
import javax.sql.rowset.FilteredRowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

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

/**
 * @author liming.gong
 */
public class JdbcRowSetWithListern extends PostgreConfig{
    public static void main(String [] args) {
        try {
            Class.forName("org.postgresql.Driver");
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
}
