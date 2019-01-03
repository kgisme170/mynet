package pkg;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.ws.Endpoint;

@WebService(endpointInterface = "pkg.ICalWebservice")
@SOAPBinding(style=Style.RPC)
public class CalWebservice implements ICalWebservice {

    @Override
    public int add(int x, int y) {
        System.out.println("CalWebservice.add");
        return x + y;
    }

    public static void main(String[] args) {
        Endpoint endpoint = Endpoint.publish("http://localhost:8080/cal", new CalWebservice());
        System.out.println(endpoint.isPublished());
    }
}