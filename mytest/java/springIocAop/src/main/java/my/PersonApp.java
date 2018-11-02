package my;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class PersonApp {

    public static void main(String[] args) {
        FileSystemXmlApplicationContext ctx =
                new FileSystemXmlApplicationContext("application.xml");
        Person person = (Person) ctx.getBean("person");
        person.run(1);

        person.run(1, "test");
        person.say();
    }
}