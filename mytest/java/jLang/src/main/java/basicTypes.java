import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

        Path path = Paths.get("core2.java");
        try {
            String contents = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
            System.out.println(contents);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String sp : Pattern.compile("\\PL+").splitAsStream("ab cd ef:gh,xy").collect(Collectors.toSet())) {
            System.out.println(sp); // PL+代表非字母都当成是分隔符, \\PN+非数字 \\PZ+非分隔符, \\PS+非符号
        }
        Charset charset = StandardCharsets.UTF_8; //注意: StandardCharsets是jdk1.7添加的

        //从字符集中创建相应的编码和解码器
        CharsetEncoder encoder = charset.newEncoder();
        CharsetDecoder decoder = charset.newDecoder();

        //构造一个buffer
        CharBuffer charBuffer = CharBuffer.allocate(64);
        charBuffer.put('你');
        charBuffer.put('好');
        charBuffer.put('!');
        charBuffer.flip();

        try {
            //将字符序列转换成字节序列
            ByteBuffer bb = encoder.encode(charBuffer);
            for (; bb.hasRemaining(); ) {
                System.out.print(bb.get() + " ");
            }

            //将字节序列转换成字符序列
            bb.flip();
            CharBuffer cb = decoder.decode(bb);
            System.out.println("\n" + cb);
        } catch (CharacterCodingException e) {
            e.printStackTrace();
        }
    }
}