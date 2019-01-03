package pkg;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Endpoint;
import java.util.Date;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
interface IService {
    String hello(String username);
}

public class ServiceMain {
    public static void main(String[] args) {
        String address = "http://localhost:7777/myService";
        Endpoint endpoint = Endpoint.publish(address, new ServiceImp());
        System.out.println(endpoint.isPublished());
    }
}