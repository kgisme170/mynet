import com.sun.rowset.WebRowSetImpl;

import javax.sql.RowSet;
import javax.sql.rowset.*;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liming.gong
 */

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
public class UseWebRowSet extends PostgreConfig {
    public static void main(String[] args) {
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
}