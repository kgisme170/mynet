import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class basicTypes {
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
        int[] a = new int[]{4, 1, 2, 3};
        int[] a2 = Arrays.copyOf(a, a.length);
        Arrays.sort(a2);
        System.out.println(Arrays.toString(a));

        int[][] a3 = (int[][]) Array.newInstance(a.getClass(), a.length);
        int[] a4 = (int[]) Array.newInstance(int.class, a.length);
        System.out.println(a3);
        System.out.println(a4);
        int x = 1;
        assert x < 0;
        Set<String> s1 = ConcurrentHashMap.newKeySet();
    }
}