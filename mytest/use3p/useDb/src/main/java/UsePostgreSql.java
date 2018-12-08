import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 * @author liming.glm
 */
public class UsePostgreSql {
    public static void main(String args[]) {
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
/*
安装和初始化postgre sql
brew install postgresql
initdb /usr/local/var/postgres
pg_ctl -D /usr/local/var/postgres -l /usr/local/var/postgres/server.log start
createdb

初始化用户和口令
psql
>
x=# CREATE USER postgres WITH PASSWORD 'password';
CREATE ROLE
x=# DROP DATABASE postgres;
DROP DATABASE
x=# CREATE DATABASE postgres OWNER postgres;
CREATE DATABASE
x=# GRANT ALL PRIVILEGES ON DATABASE postgres to postgres;
GRANT
x=# ALTER ROLE postgres CREATEDB;
ALTER ROLE
x=# \q

建立表的数据:
psql -U postgres -d postgres
>
postgres=> CREATE TABLE COMPANY(
postgres(>    ID INT PRIMARY KEY     NOT NULL,
postgres(>    NAME           TEXT    NOT NULL,
postgres(>    AGE            INT     NOT NULL,
postgres(>    ADDRESS        CHAR(50),
postgres(>    SALARY         REAL,
postgres(>    JOIN_DATE  DATE
postgres(> );
CREATE TABLE
postgres=> INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY,JOIN_DATE) VALUES (1, 'Paul', 32, 'California', 20000.00 ,'2001-07-13');
INSERT 0 1
postgres=> INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,JOIN_DATE) VALUES (2, 'Allen', 25, 'Texas', '2007-12-13');
INSERT 0 1

postgres=> INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY,JOIN_DATE) VALUES (4, 'Mark', 25, 'Rich-Mond ', 65000.00, '2007-12-13' ), (5, 'David', 27, 'Texas', 85000.00 , '2007-12-13');

\l看到所有的db
\d看到当前所有的表

---------------------
注意要保证
$cat /etc/hosts
127.0.0.1   localhost
255.255.255.255 broadcasthost  #有这一行
::1             localhost #可选

 */