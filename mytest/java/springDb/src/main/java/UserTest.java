import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
// create table t3(id int primary key not null, username char(20) default null, password char(20) not null);
public class UserTest {
    public static void main(String[] args){
        new UserTest().demo1();
    }
    public void demo1() {
        User user = new User();
        user.setId(3);
        user.setUsername("admin");
        user.setPassword("123456");

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        IUserDAO dao = (IUserDAO) applicationContext.getBean("userDao");
        dao.addUser(user);
    }

    public void demo2() {
        User user = new User();
        user.setId(1);
        user.setUsername("admin");
        user.setPassword("admin");

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        IUserDAO dao = (IUserDAO) applicationContext.getBean("userDao");
        dao.updateUser(user);
    }

    public void demo3() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        IUserDAO dao = (IUserDAO) applicationContext.getBean("userDao");
        dao.deleteUser(3);
    }

    public void demo4() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        IUserDAO dao = (IUserDAO) applicationContext.getBean("userDao");
        String name = dao.searchUserName(1);
        System.out.println(name);
    }

    public void demo5() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        IUserDAO dao = (IUserDAO) applicationContext.getBean("userDao");
        User user = dao.searchUser(1);
        System.out.println(user.getUsername());
    }

    public void demo6() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        IUserDAO dao = (IUserDAO) applicationContext.getBean("userDao");
        List<User> users = dao.findAll();
        System.out.println(users.size());
    }
}