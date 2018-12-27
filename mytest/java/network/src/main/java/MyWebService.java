import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.util.Arrays;
import java.util.List;

/**
 * @author liming.gong
 */
@WebService(targetNamespace = "MyWebService")
public class MyWebService {
    public String transWords(String words) {
        String res = "";
        for (char ch : words.toCharArray()) {
            res += ch + ",";
        }
        return res;
    }

    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8099/service/function", new MyWebService());
        System.out.println("OK");
    }
}