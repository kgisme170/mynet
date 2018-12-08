import java.sql.*;
/**
 * @author liming.glm
 */
public class MysqlJdbc {

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (java.lang.ClassNotFoundException e) {
            System.out.println("=>不能加载驱动");
        }
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/db1",
                    "root",
                    "mypassword");
            System.out.println("连接成功");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from t1");
            if (rs != null) {
                ResultSetMetaData rsmd = rs.getMetaData();
                int columns = rsmd.getColumnCount();
                for (int i = 1; i <= columns; i++) {
                    if (i > 1) {
                        System.out.print(";");
                    }
                    System.out.print(rsmd.getColumnName(i) + " ");
                }
                System.out.println("\n_______\n");
                while (rs.next()) {
                    System.out.print(rs.getInt("id") + "  ");
                    System.out.print(rs.getString("name") + "  ");
                    System.out.println();
                }
            }
            System.out.println("ok");
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            System.out.println("=>连接失败:" + e.getMessage());
        }
    }
}