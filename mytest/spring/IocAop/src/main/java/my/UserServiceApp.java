package my;

import org.springframework.context.support.FileSystemXmlApplicationContext;
/**
 * @author liming.glm
 */
public class UserServiceApp {
    public static void main(String[] args){
        String fileName = "../../../src/bean.xml";
        String s = UserServiceApp.class.getResource(".") + fileName;
        FileSystemXmlApplicationContext context =
                new FileSystemXmlApplicationContext(s);
        UserService sv = (UserService)context.getBean("userService");
        sv.save();
    }
}
