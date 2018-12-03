import java.io.StringReader;
import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;
public class useJaxb {
    public static void main(String[] args) {
        try {
            String xmlString = "<message>HELLO!</message> ";
            JAXBContext jc = JAXBContext.newInstance(String.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            StreamSource xmlSource = new StreamSource(new StringReader(xmlString));
            JAXBElement<String> je = unmarshaller.unmarshal(xmlSource, String.class);
            System.out.println(je.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}