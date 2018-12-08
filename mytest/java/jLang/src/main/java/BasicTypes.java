import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BasicTypes {
    public void f() {
        System.out.println("BasicTypes.f()");
    }

    /**
     * PL+代表非字母都当成是分隔符, \\PN+非数字 \\PZ+非分隔符, \\PS+非符号
     */
    static Set<String> pt = Pattern.compile("\\PL+").splitAsStream("ab cd ef:gh,xy").collect(Collectors.toSet());

    public static void main(String[] args) {
        int i = 0b1000010 & 0b1000;
        System.out.println(i);
        System.out.println(LocalDate.now());
        System.out.printf("%tc\n", new Date());
        System.out.printf("%1$s\n", new Date());
        try {
            Scanner c = new Scanner(Paths.get("/tmp/file1.txt"));
            System.out.println(c.next());
        } catch (IOException e) {
        }
        BigInteger b = BigInteger.TEN;
        BigInteger b2 = BigInteger.valueOf(232432);
        System.out.println(b.add(b2));
        int x = 1;
        assert x < 0;
        Set<String> s1 = ConcurrentHashMap.newKeySet();

        for (String sp : pt) {
            System.out.println(sp);
        }
    }
}