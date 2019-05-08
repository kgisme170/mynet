import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
/**
 * @author liming.gong
 */
public class ReadFileToString {
    public static void main(String [] args) throws IOException {
        String fileContent="";
        try {
            File f = new File(args[1]);
            byte[] bf = new byte[(int)f.length()];
            new FileInputStream(f).read(bf);
            fileContent = new String(bf);
            System.out.println(fileContent);
        } catch (FileNotFoundException e) {
            // handle file not found exception
        } catch (IOException e) {
            // handle IO-exception
        }

        Path path = Paths.get("core2.java");
        try {
            String contents = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
            System.out.println(contents);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}