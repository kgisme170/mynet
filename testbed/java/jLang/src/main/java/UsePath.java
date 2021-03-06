import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
/**
 * @author liming.gong
 */
public class UsePath {
    public static void main(String[] args) {
        try {
            System.out.println(System.getProperty("user.dir"));
            System.out.println(System.getProperty("java.class.path"));
            URL url = new UsePath().getClass().getClassLoader().getResource("");
            System.out.println(url);
            String path = url.getPath();
            System.out.println(path);
            //print null

            String fn = path + "my.txt";
            List<String> ls = Files.readAllLines(Paths.get(fn));
            ls.forEach(System.out::println);

            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream("my.zip"));
            zos.putNextEntry(new ZipEntry(fn));
            zos.closeEntry();
            zos.close();

            ZipEntry entry;
            ZipInputStream zis = new ZipInputStream(new FileInputStream("my.zip"));
            while ((entry = zis.getNextEntry()) != null) {
                System.out.println(entry.getName());
                //...
                zis.closeEntry();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}