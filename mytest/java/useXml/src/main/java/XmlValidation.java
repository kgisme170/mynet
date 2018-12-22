import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author liming.gong
 */
public class XmlValidation {
    private static String currentPath = Thread.currentThread().
            getContextClassLoader().getResource("").toString();
    private String docRoot;
    private DocumentBuilder builder;
    private SchemaFactory factory;
    public XmlValidation() {
        try {
            docRoot = new URI(currentPath).getPath();
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            f.setNamespaceAware(true);
            builder = f.newDocumentBuilder();
            factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void validateFile(String fn) {
        try {
            String p = new URI(currentPath).getPath();
            Path file = Paths.get(p, fn);

            Path xsdFile = Paths.get(p, "Employee.xsd");
            Schema schema = factory.newSchema(xsdFile.toFile());
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(file.toFile()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        XmlValidation xv = new XmlValidation();
        xv.validateFile("EmployeeRequest.xml");
        xv.validateFile("EmployeeResponse.xml");
        //xv.validateFile("employee.xml");//will fail
    }
}