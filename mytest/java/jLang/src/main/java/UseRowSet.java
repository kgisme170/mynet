import com.sun.rowset.CachedRowSetImpl;
import com.sun.rowset.JdbcRowSetImpl;
import com.sun.rowset.JoinRowSetImpl;
import com.sun.rowset.WebRowSetImpl;

import javax.sql.RowSet;
import javax.sql.rowset.*;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    // methods for handling errors
}

public class UseRowSet {
    public static void main(String[] args) throws Exception {
        /*
        JdbcRowSet jdbcRS = new JdbcRowSetImpl(conn);
        jdbcRS.setType(ResultSet.TYPE_SCROLL_INSENSITIVE);
        String sql = "SELECT * FROM customers";
        jdbcRS.setCommand(sql);
        jdbcRS.execute();
        jdbcRS.addRowSetListener(new ExampleListener());
        while (jdbcRS.next()) {
            // each call to next, generates a cursorMoved event
            System.out.println("id = " + jdbcRS.getString(1));
            System.out.println("name = " + jdbcRS.getString(2));
        }*/

        /*
        CachedRowSet crs = new CachedRowSetImpl();
        crs.setUsername(username);
        crs.setPassword(password);
        crs.setUrl(url);
        crs.setCommand(sql);
        crs.execute();
        crs.addRowSetListener(new ExampleListener());
        while (crs.next()) {
            if (crs.getInt("id") == 1) {
                System.out.println("CRS found customer1 and will remove the record.");
                crs.deleteRow();
                break;
            }
        }
        */

        /*
        WebRowSet wrs = new WebRowSetImpl();
        wrs.setUsername(username);
        wrs.setPassword(password);
        wrs.setUrl(url);
        wrs.setCommand(sql);
        wrs.execute();
        FileOutputStream ostream = new FileOutputStream("customers.xml");
        wrs.writeXml(ostream);
        */

        /*
        CachedRowSetImpl customers = new CachedRowSetImpl();
        // configuration of settings for CachedRowSet
        CachedRowSetImpl associates = new CachedRowSetImpl();
        // configuration of settings for this CachedRowSet
        JoinRowSet jrs = new JoinRowSetImpl();
        jrs.addRowSet(customers,ID);
        jrs.addRowSet(associates,ID);
        */

        /*
        RowSetFactory rsf = RowSetProvider.newFactory();
        FilteredRowSet frs = rsf.createFilteredRowSet();
        frs.setCommand("select * from customers");
        frs.execute(conn);
        frs.setFilter(new FilterExample("^[A-C].*"));

        ResultSetMetaData rsmd = frs.getMetaData();
        int columncount = rsmd.getColumnCount();
        while (frs.next()) {
            for (int i = 1; i <= columncount; i++) {
                System.out.println(
                        rsmd.getColumnLabel(i)
                                + " = "
                                + frs.getObject(i) + " ");
            }
        }
        */


    }
}