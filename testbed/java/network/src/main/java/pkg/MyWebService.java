package pkg;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;

/**
 * @author liming.gong
 */
@WebService(endpointInterface = "pkg.IWebService")
public class MyWebService implements IWebService {
    @Override
    public String transWords(String words) {
        String res = "";
        for (char ch : words.toCharArray()) {
            res += ch + ",";
        }
        return res;
    }

    public static void main(String[] args) {
        Endpoint endpoint = Endpoint.publish("http://localhost:8099/service/function", new MyWebService());
        System.out.println(endpoint.isPublished());
    }
}