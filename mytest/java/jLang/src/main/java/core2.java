import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingByConcurrent;
import static java.util.stream.Collectors.toSet;
class base implements Serializable{
    protected String field = "xyz";
}
public class core2 {
    public static Optional<Double> inserve(Double x) {
        return x == 0 ? Optional.empty() : Optional.of(1 / x);
    }

    public static Optional<Double> squareRoot(Double x) {
        return x < 0 ? Optional.empty() : Optional.of(Math.sqrt(x));
    }

    public static void main(String[] args) {
        Stream<Double> s1 = Stream.generate(Math::random);
        Stream<String> s2 = Stream.generate(() -> "echo");
        Stream<Integer> si = Stream.iterate(1, n -> n + 1);//OK
        IntStream s = IntStream.iterate(1, n -> n + 1);

        String[] words = new String[]{"Hello", "World"};
        Stream<String> s3 = Stream.of(words)
                .flatMap(str -> Arrays.stream(str.split("")))
                .distinct();
        s3.forEach(System.out::print);
        //List<String> a = s3.collect(Collectors.toList());//已经是terminate状态
        Optional<Double> d = Optional.of(-4.0).flatMap(core2::inserve).flatMap(core2::squareRoot);
        System.out.println();
        System.out.println(d);
        System.out.println(Locale.getDefault().getDisplayName());

        Locale locale = new Locale("en", "US", "WIN");
        System.out.println("step1");
        System.out.println(locale.getLanguage());
        {
            Stream<Locale> sl = Stream.of(Locale.getAvailableLocales());
            Map<String, List<Locale>> map = sl.collect(Collectors.groupingBy(Locale::getCountry));
            for (Map.Entry<String, List<Locale>> e : map.entrySet()) {
                System.out.println(e.getKey());
                System.out.println(e.getValue());
            }
        }
        System.out.println("step2");
        {
            Stream<Locale> sl = Stream.of(Locale.getAvailableLocales());
            Map<String, List<Locale>> map = sl.collect(Collectors.groupingBy(Locale::getCountry));
        }
        System.out.println("step3");
        {
            Stream<Locale> sl = Stream.of(Locale.getAvailableLocales());
            Map<Boolean, List<Locale>> map2 = sl.collect(Collectors.partitioningBy(l->l.getLanguage().equals("en")));
        }
        System.out.println("step4");
        System.out.println("step3");
        {
            Stream<Locale> sl = Stream.of(Locale.getAvailableLocales());
            Map<String, Set<Locale>> map3 = sl.collect(Collectors.groupingBy(Locale::getCountry, toSet()));
        }

        Path path = Paths.get("core2.java");
        try {
            String contents = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
            System.out.println(contents);
        }catch(IOException e){
            e.printStackTrace();
        }

        Stream<Double> sd = Stream.generate(Math::random);//0-1之间的小数
        for(Double db : sd.limit(10).collect(Collectors.toSet())){
            System.out.println(db);
        }
        for(String sp : Pattern.compile("\\PL+").splitAsStream("ab cd ef:gh,xy").collect(Collectors.toSet())){
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
        }catch(CharacterCodingException e){
            e.printStackTrace();
        }

        Arrays.stream("abc".split("")).forEach(System.out::println);//first
        Arrays.stream("abc".split("")).peek(new Consumer<String>() {//second
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        }).count();
        Optional<String> os = Arrays.stream("xyzabc".split("")).max(String::compareToIgnoreCase);
        System.out.println(os);
        System.out.println(Arrays.stream("xyzabc".split("")).findFirst());
        System.out.println(Arrays.stream(new int[]{2,4,6,8}).allMatch(x->x%2==0));
        IntSummaryStatistics iss = Arrays.stream(new String[]{"abc","zed","hello"}).collect(Collectors.summarizingInt(String::length));
        System.out.println(iss.getSum() + ", " + iss.getAverage() + ", " + iss.getCount() + ", " + iss.getMax());
        Integer[] ia = Stream.of(1,3,5,7).toArray(Integer[]::new);

        Map<Integer, Long> m = Arrays.asList(new String[]{"abc","zed","hello"}).parallelStream()
                .collect(groupingByConcurrent(String::length, counting()));
        System.out.println(m);
    }
}