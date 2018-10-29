package my;

import org.springframework.context.support.FileSystemXmlApplicationContext;

public class UserServiceApp {
    public static void main(String[] args){
        FileSystemXmlApplicationContext context =
                new FileSystemXmlApplicationContext("bean.xml");
        UserService s = (UserService)context.getBean("userService");
        s.save();
    }
}
