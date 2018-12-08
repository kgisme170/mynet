import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
/**
 * @author liming.glm
 */
public class UseJaxp {
    public static void main(String[] args) {
        String xml = "<message>HELLO!</message>";
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            try {
                Document doc = db.parse(is);
                String message = doc.getDocumentElement().getTextContent();
                System.out.println(message);
            } catch (SAXException e) {
            } catch (IOException e) {
            }
        } catch (ParserConfigurationException e1) {
        }
    }
}