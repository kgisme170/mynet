import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.util.Date;

@WebService
interface IService {
    void hello(@WebParam(name="username") String username);
}

@WebService(targetNamespace = "ServiceImpl", endpointInterface="IService")
class ServiceImp implements IService{
    @Override
    public void hello(@WebParam(name = "username") String username) {
        System.out.println("hello " + username + " now is " + new Date());
    }
}

public class ServiceMain {
    public static void main(String[] args) {
        String address = "http://localhost:7777/myService";
        Endpoint.publish(address, new ServiceImp());
        System.out.println("OK");
    }
}