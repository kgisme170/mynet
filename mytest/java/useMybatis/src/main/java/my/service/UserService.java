package my.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import my.beans.UserBean;
import my.tools.DBTools;
import my.mapper.UserMapper;

public class UserService {

    public static void main(String[] args) {
        System.out.println("enter main");
        insertUser();
//        deleteUser();
//        selectUserById();
//        selectAllUser();
    }


    /**
     * 新增用户
     */
    private static void insertUser() {
        System.out.println("enter 1");
        SqlSession session = DBTools.getSession();
        System.out.println("enter 2");
        UserMapper mapper = session.getMapper(UserMapper.class);
        System.out.println("enter 3");
        UserBean user = new UserBean("懿", "1314520", 7000.0);
        System.out.println("enter 4");
        try {
            mapper.insertUser(user);
            System.out.println("5");
            System.out.println(user.toString());
            session.commit();
            System.out.println("6");
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        }
    }


    /**
     * 删除用户
     */
    private static void deleteUser() {
        SqlSession session = DBTools.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        try {
            mapper.deleteUser(1);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        }
    }


    /**
     * 根据id查询用户
     */
    private static void selectUserById() {
        SqlSession session = DBTools.getSession();
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

    /**
     * 查询所有的用户
     */
    private static void selectAllUser() {
        SqlSession session = DBTools.getSession();
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
