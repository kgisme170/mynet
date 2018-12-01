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
        //得到解析工厂DocumentBuilderFactory
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //得到解析器DocumentBuilder
        DocumentBuilder builder = factory.newDocumentBuilder();
        //解析指定的XML文档，得到代表内存DOM树的Document对象
        Document document = builder.parse(currentPath + "/book.xml");
        test(document);
    }

    // 2、遍历所有元素节点:打印元素的名称
    public static void iterate(Node node) {
        //判断当前节点是不是一个元素节点
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            //如果是：打印他的名称
            System.out.println(node.getNodeName());
        }
        //查找子节点
        NodeList nodeList = node.getChildNodes();
        int len = nodeList.getLength();
        for (int i = 0; i < len; i++) {
            Node n = nodeList.item(i);
            String text = n.getTextContent();
            System.out.println(text);
        }
    }

    //8、添加一个出版社属性给第二本书
    public static void test(Document document) throws Exception {
        Node n = document.getElementsByTagName("书").item(0);//打印指定属性的取值
        iterate(n);
        n.getParentNode().removeChild(n);
        Element e = (Element) n;
        System.out.println(e.getAttribute("出版社"));
        Node firstBookNode = document.getElementsByTagName("书").item(0);
        //把新节点挂接到第一本书上
        firstBookNode.appendChild(e);
        Node n1 = document.getElementsByTagName("书").item(1);
        //打印指定属性的取值
        Element e1 = (Element) n1;
        e.setAttribute("出版社", "复旦大学出版社");
        System.out.println(e.getAttribute("ISBN"));
        Element e2 = document.createElement("批发价");
        e2.setTextContent("35.00元");
        //找到第一本书的售价
        Node firstPrice = document.getElementsByTagName("售价").item(0);
        //在售价的前面加入新建的元素：增加子元素一定要用父元素来增加
        firstPrice.getParentNode().insertBefore(e, firstPrice);
        //把内存中Document树写回XML文件中
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer ts = factory.newTransformer();
        ts.transform(new DOMSource(document), new StreamResult(currentPath + "/book.xml"));
    }
}