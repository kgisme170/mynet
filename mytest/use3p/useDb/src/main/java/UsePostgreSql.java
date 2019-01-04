import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 * @author liming.gong
 */
public class UsePostgreSql {
    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres",
                    "postgres",
                    "password");
            conn.setAutoCommit(false);

            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(
                    "SELECT name, age FROM company ORDER BY age");
            System.out.println("----------------");
            while (rs.next()) {
                StringBuilder builder = new StringBuilder(rs.getString(1));
                builder.append("\t");
                builder.append(rs.getInt(2));
                System.out.println(builder.toString());
            }
            rs.close();
            s.close();
            System.out.println("关闭返回的 set 和 statement");
            conn.commit();
            conn.close();
            System.out.println("提交 transaction 并关闭连接");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}