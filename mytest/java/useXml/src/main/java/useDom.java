import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class useDom {
    static String currentPath = Thread.currentThread().getContextClassLoader().getResource("").toString();

    public static void main(String[] args) throws Exception {
        System.out.println(currentPath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(currentPath + "/book.xml");
        test(document);
    }

    public static void iterate(Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            System.out.println(node.getNodeName());
        }
        NodeList nodeList = node.getChildNodes();
        int len = nodeList.getLength();
        for (int i = 0; i < len; i++) {
            Node n = nodeList.item(i);
            String text = n.getTextContent();
            System.out.println(text);
        }
    }

    public static void test(Document document) throws Exception {
        Node n = document.getElementsByTagName("book").item(0);
        iterate(n);
        Element e = (Element) n;
        e.setAttribute("press", "AAA press");
        Node bookshelf = n.getParentNode();
        bookshelf.appendChild(e);
        bookshelf.appendChild(e);
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer ts = factory.newTransformer();
        ts.transform(new DOMSource(document), new StreamResult(currentPath + "/book.xml"));
    }
}