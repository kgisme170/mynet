import com.alibaba.druid.pool.DruidDataSource;

import java.sql.*;
/**
 * @author liming.glm
 */
public class UseDruid {
    public static void main(String[] args) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
        dataSource.setUsername("postgres");
        dataSource.setPassword("password");
        try {
            Connection conn = dataSource.getConnection();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}