import java.io.IOException;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class useXerces {
    public static void main(String[] args) {
        String xml = "<message>HELLO!</message>";
        DOMParser parser = new DOMParser();
        try {
            parser.parse(new InputSource(new java.io.StringReader(xml)));
            Document doc = parser.getDocument();
            String message = doc.getDocumentElement().getTextContent();
            System.out.println(message);
        } catch (SAXException e) {
        } catch (IOException e) {
        }
    }
}