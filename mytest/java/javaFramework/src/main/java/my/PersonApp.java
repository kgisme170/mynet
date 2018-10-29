package my;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class PersonApp {

    public static void main(String[] args) {
        String pwd = System.getProperty("user.dir");
        System.out.println(pwd);
        String classPath = new PersonApp().getClass().getClassLoader().getResource("").getPath();
        System.out.println(classPath);
        FileSystemXmlApplicationContext ctx =
                new FileSystemXmlApplicationContext("application.xml");
        Person person = (Person) ctx.getBean("person");
        person.run(1);

        person.run(1, "test");
        person.say();
    }
}