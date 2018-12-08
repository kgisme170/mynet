import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.net.URI;
/**
 * @author liming.glm
 */
public class UseTransform {
    static String currentPath = Thread.currentThread().getContextClassLoader().getResource("").toString();

    public static void main(String [] args) throws Exception {
        String path = currentPath + "phone.xml";
        String xml = new URI(path).getPath();

        String pathXsl = currentPath + "phone.xsl";
        String xsl = new URI(pathXsl).getPath();

        StreamSource source = new StreamSource(xml);
        StreamSource styleSource = new StreamSource(xsl);
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(styleSource);

        StreamResult result = new StreamResult(System.out);
        transformer.transform(source, result);
    }
}