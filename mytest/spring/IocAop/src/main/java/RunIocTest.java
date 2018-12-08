import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
/**
 * @author liming.glm
 */
public class RunIocTest {
    public static void main(String[] args) {
        String fileName = "../../src/applicationContext.xml";
        String s = RunIocTest.class.getResource(".") + fileName;
        ApplicationContext factory = new FileSystemXmlApplicationContext(s);
        ArrayIocTest test = (ArrayIocTest) factory.getBean("arrayIocTest");
        test.testData();
    }
}