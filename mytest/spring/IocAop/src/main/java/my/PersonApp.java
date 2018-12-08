package my;
import org.springframework.context.support.FileSystemXmlApplicationContext;
/**
 * @author liming.glm
 */
public class PersonApp {
    public static void main(String[] args) {
        String fileName = "../../../src/application.xml";
        String s = PersonApp.class.getResource(".") + fileName;
        FileSystemXmlApplicationContext ctx =
                new FileSystemXmlApplicationContext(s);
        Person person = (Person) ctx.getBean("person");
        person.run(1);
        person.run(1, "test");
        person.say();
    }
}