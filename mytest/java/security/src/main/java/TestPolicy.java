import java.io.FileWriter;
import java.io.IOException;

/**
 * @author liming.gong
 * java -classpath . -Djava.security.manager -Djava.security.policy=../../src/myPolicy.txt TestPolicy
 */
public class TestPolicy {
    public static void main(String[] args) {
        FileWriter writer;
        try {
            writer = new FileWriter("testPolicy.txt");
            writer.write("hello");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}