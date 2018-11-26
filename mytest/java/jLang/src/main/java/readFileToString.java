import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class readFileToString {
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
    }
}