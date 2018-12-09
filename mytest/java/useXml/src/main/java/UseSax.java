import org.xml.sax.*;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
/**
 * @author liming.gong
 */
public class UseSax {
    static String currentPath = Thread.currentThread().getContextClassLoader().getResource("").toString();

    public static void main(String[] args) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLReader reader = parser.getXMLReader();
        reader.setContentHandler(new MyContentHandler());
        reader.parse(currentPath + "/book.xml");
    }
}
class MyContentHandler implements ContentHandler {
    @Override
    public void setDocumentLocator(Locator locator) {
    }

    @Override
    public void startDocument() throws SAXException {
        System.out.println("解析到了文档的开始");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        System.out.println("解析到了元素的开始：" + qName);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        System.out.println("文本内容：" + new String(ch, start, length));
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        System.out.println("解析到了元素的结束：" + qName);
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("解析到了文档的结束");
    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
        System.out.println("endPrefixMapping prefix: " + prefix);
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
    }

    @Override
    public void processingInstruction(String target, String data) throws SAXException {
        System.out.println("processingInstruction target: " + target);
    }

    @Override
    public void skippedEntity(String name) throws SAXException {
        System.out.println("skippedEntity name: " + name);
    }

    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        System.out.println("startPrefixMapping prefix: " + prefix);
    }
}