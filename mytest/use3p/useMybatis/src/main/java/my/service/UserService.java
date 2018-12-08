package my.service;
import java.util.List;
import org.apache.ibatis.session.SqlSession;

import my.beans.UserBean;
import my.tools.DbTools;
import my.mapper.UserMapper;
/*
create table t_user(
id SERIAL PRIMARY KEY,
username VARCHAR(20) default NULL,
password VARCHAR(20) default NULL,
account double precision);
 */

/**
 * @author liming.glm
 */
public class UserService {

    public static void main(String[] args) {
        System.out.println("enter main");
        insertUser();
//        deleteUser();
//        selectUserById();
        //selectAllUser();
    }

    private static void insertUser() {
        SqlSession session = DbTools.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        UserBean user = new UserBean("懿", "1314520", 7000.0);
        try {
            mapper.insertUser(user);
            System.out.println(user.toString());
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        }
    }

    private static void deleteUser() {
        SqlSession session = DbTools.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        try {
            mapper.deleteUser(1);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        }
    }

    private static void selectUserById() {
        SqlSession session = DbTools.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        try {
            UserBean user = mapper.selectUserById(2);
            System.out.println(user.toString());

            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        }
    }

    private static void selectAllUser() {
        SqlSession session = DbTools.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        try {
            List<UserBean> user = mapper.selectAllUser();
            System.out.println(user.toString());
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        }
    }
}