package pkg;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService
interface IService {
    /**
     * hello + [username]
     * @param username
     * @return
     */
    String hello(String username);
}

/**
 * @author liming.gong
 */
public class ServiceMain {
    public static void main(String[] args) {
        String address = "http://localhost:7777/myService";
        Endpoint endpoint = Endpoint.publish(address, new ServiceImpl());
        System.out.println(endpoint.isPublished());
    }
}