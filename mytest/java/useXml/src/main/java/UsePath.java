import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author liming.gong
 */
public class UsePath {
    public static void main(String[] args) {
        String currentPath = Thread.currentThread().
                getContextClassLoader().getResource("").toString();
        try {
            String p = new URI(currentPath).getPath();
            System.out.println(p);
            Path file = Paths.get(p, "book.xml");
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            f.setNamespaceAware(true);
            DocumentBuilder builder = f.newDocumentBuilder();
            Document doc = builder.parse(file.toFile());

            XPath path = XPathFactory.newInstance().newXPath();
            XPathExpression expr = path.compile("//book[Author='John']/name/text()");
            String result = (String) expr.evaluate(doc, XPathConstants.STRING);
            System.out.println(result);

            XPathExpression expr2 = path.compile("//book/name/text()");
            NodeList nl = (NodeList) expr2.evaluate(doc, XPathConstants.NODESET);
            System.out.println(nl.getLength());
            for (int i = 0; i < nl.getLength(); i++) {
                System.out.println(nl.item(i).getNodeValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}