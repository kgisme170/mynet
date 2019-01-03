package pkg;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;
/**
 * @author liming.gong
 */
public class ServiceClient {
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://localhost:7777/myService?wsdl");
        QName qName = new QName("http://pkg/", "ServiceImpService");
        Service service = Service.create(url, qName);
        IService service1 = service.getPort(IService.class);
        System.out.println(service1.hello("John"));
    }
}