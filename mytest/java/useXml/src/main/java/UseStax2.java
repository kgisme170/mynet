import javax.xml.stream.*;
import java.io.FileReader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
/**
 * @author liming.gong
 */
public class UseStax2 {
    static String currentPath = Thread.currentThread().getContextClassLoader().getResource("").toString();

    public static void main(String[] args) throws Exception {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        String path = currentPath + "book.xml";
        String cur = new URI(path).getPath();
        System.out.println(path);
        System.out.println(cur);
        System.out.println(Files.exists(Paths.get(cur)));
        FileReader fileReader = new FileReader(cur);
        XMLStreamReader reader = factory.createXMLStreamReader(fileReader);
        try {
            int event = reader.getEventType();
            while (true) {
                switch (event) {
                    case XMLStreamConstants
                            .START_ELEMENT: {
                        System.out.println("start:" + reader.getLocalName() + ", attr[0] = " + reader.getAttributeName(0));
                        break;
                    }
                    case XMLStreamConstants
                            .START_DOCUMENT: {
                        System.out.println("end of document");
                        break;
                    }
                    case XMLStreamConstants
                            .END_DOCUMENT: {
                        System.out.println("end of document");
                        break;
                    }
                    case XMLStreamConstants
                            .CHARACTERS: {
                        String content = reader.getText();
                        if (content != null && !content.isEmpty() && content.length() != 0) {
                            System.out.println(", 文本 = " + content + ", " + content.length());
                        }
                    }
                }
                if (!reader.hasNext()) {
                    break;
                }
                event = reader.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}