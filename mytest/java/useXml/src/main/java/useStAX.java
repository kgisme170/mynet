import javax.xml.stream.EventFilter;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.XMLEvent;
import java.io.FileReader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

public class useStAX {
    static String currentPath = Thread.currentThread().getContextClassLoader().getResource("").toString();

    public static void main(String[] args) {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        String path = currentPath + "book.xml";
        try {
            String cur = new URI(path).getPath();
            System.out.println(path);
            System.out.println(cur);
            System.out.println(Files.exists(Paths.get(cur)));
            FileReader reader = new FileReader(cur);
            XMLEventReader eventReader = factory.createXMLEventReader(reader);
            XMLEventReader filteredEventReader =
                    factory.createFilteredReader(eventReader, new EventFilter() {
                        public boolean accept(XMLEvent event) {
                            boolean ipi = event.isProcessingInstruction();
                            if (ipi) {
                                System.out.println("accept ------------------------------");
                            }
                            return (!ipi);
                        }
                    });
            while (filteredEventReader.hasNext()) {
                XMLEvent e = (XMLEvent) filteredEventReader.next();
                System.out.println(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}