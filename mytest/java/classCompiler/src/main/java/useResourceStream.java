import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class useResourceStream {
    public static void main(String[] args) {
        InputStream myTxt = useResourceStream.class.getResourceAsStream("pack01/my2.txt");
        String result = new BufferedReader(new InputStreamReader(myTxt))
                .lines().collect(Collectors.joining(System.lineSeparator()));
        System.out.println(result);
    }
}