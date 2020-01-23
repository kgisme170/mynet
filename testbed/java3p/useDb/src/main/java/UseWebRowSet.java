import com.sun.rowset.WebRowSetImpl;

import javax.sql.rowset.*;
import java.io.FileOutputStream;

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
