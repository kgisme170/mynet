import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import static java.lang.System.getProperty;

public class runIocTest {
    public static void main(String[] args) {
        String fileName = "../../src/applicationContext.xml";
        String s = testFastJsonComplex.class.getResource(".") + fileName;
        ApplicationContext factory = new FileSystemXmlApplicationContext(s);
        ArrayIocTest test = (ArrayIocTest) factory.getBean("arrayIocTest");
        test.testData();
    }
}