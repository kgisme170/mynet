package pkg;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;
/**
 * @author liming.gong
 */
public class CalClient {
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://localhost:8080/cal?wsdl");
        QName qName = new QName("http://pkg/", "CalWebserviceService");
        Service service = Service.create(url, qName);
        ICalWebservice calWebservice = service.getPort(ICalWebservice.class);
        System.out.println(calWebservice.add(10, 20));
    }
}