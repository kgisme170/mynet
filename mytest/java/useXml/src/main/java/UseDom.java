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
/**
 * @author liming.glm
 */
public class UseDom {
    static String currentPath = Thread.currentThread().getContextClassLoader().getResource("").toString();

    public static void main(String[] args) throws Exception {
        System.out.println(currentPath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(currentPath + "/book.xml");

        //root
        Element e = document.getDocumentElement();
        System.out.println(e.getNodeName());
        Node f = document.getFirstChild();
        System.out.println(f.getNodeName());
        test(document);
        System.out.println("----------------");
        Node firstChild = document.getFirstChild();

        NodeList list = firstChild.getChildNodes();
        int length = list.getLength();
        for (int i = 0; i < length; ++i) {
            System.out.println("begin+++++++++++");
            System.out.println(list.item(i).getNodeName() + '_' + list.item(i).getTextContent() + '_' + list.item(i).getLocalName());
            System.out.println("end-------------");
        }
        System.out.println("----------------");

        System.out.println(firstChild.getChildNodes().getLength());
        System.out.println(firstChild.getNodeType());
        System.out.println(firstChild.getNodeName());

        System.out.println("----------------");
        Node root = document.getDocumentElement();
        System.out.println(root.getChildNodes().getLength());
        System.out.println(root.getNodeType());
        System.out.println(root.getNodeName());
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