package pkg;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

public class MyWebClient {
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://localhost:8099/service/function?wsdl");
        QName qName = new QName("http://pkg/", "MyWebServiceService");
        Service service = Service.create(url, qName);
        IWebService service1 = service.getPort(IWebService.class);
        System.out.println(service1.transWords("John"));
    }
}
