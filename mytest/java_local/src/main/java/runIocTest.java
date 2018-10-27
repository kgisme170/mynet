import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class runIocTest {
    public static void main(String[] args) {
        ApplicationContext factory = new FileSystemXmlApplicationContext("applicationContext.xml");
        ArrayIocTest test = (ArrayIocTest) factory.getBean("arrayIocTest");
        test.testData();
    }
}