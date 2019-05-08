import com.sun.rowset.CachedRowSetImpl;

import javax.sql.rowset.CachedRowSet;

/**
 * @author liming.gong
 */
public class UseCachedRowSet extends PostgreConfig {
    public static void main(String[] args) {
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
}