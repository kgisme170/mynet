import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;

/**
 * @author liming.gong
 */
public class CoreJava2 {
    public static void main(String[] args) {
        List<String> list = Arrays.asList(new String[]{"xyz", "abc"});
        List<String> list2 = list.parallelStream()
                .filter(w -> w.length() > 2).collect(Collectors.toList());
        System.out.println(list2);

        Object[] aS = list.toArray();
        System.out.println(aS);

        Stream s = Stream.of("a1", "b1");

        s.forEach(System.out::println);
        Stream.generate(() -> "abc").limit(10).forEach(System.out::println);
        Stream<BigInteger> sb =
                Stream.iterate(BigInteger.ONE, n -> n.add(BigInteger.ONE));
        sb.limit(10).forEach(System.out::println);

        Stream.Builder<String> bd = Stream.builder();
        bd.accept("abc");
        bd.accept("xyz");
        bd.add("kkk");
        Stream<String> ss = bd.build().parallel();
        ss.forEach(System.out::println);

        Arrays.stream(new String[]{"a0", "a1", "a2", "a3"}, 1, 3)
                .forEach(System.out::println);

        Arrays.stream(new String[]{"c0", "a1", "a2", "a3"})
                .map(new MyMapper()).forEach(System.out::println);

        Stream sf1 = Stream.of("a1", "b1").flatMap(str -> Arrays.stream(str.split("")));
        //System.out.println(sf1.count()); // --->will print "4"
        sf1.peek(str -> System.out.println(str)).collect(Collectors.toList());

        Stream.of("one", "two", "three", "four")
                .peek(e -> System.out.println("Peeked value: " + e))
                .map(String::toUpperCase)
                .peek(e -> System.out.println("Mapped value: " + e))
                .collect(Collectors.toList());

        List<Integer> values = Arrays.asList(3, 4, 5, 6);
        System.out.println(values.stream().filter(x -> x > 4).reduce((x, y) -> x + y));
        System.out.println(values.stream().reduce(0, (x, y) -> x + y));

        Optional<String> os = Arrays.stream(new String[]{"c0", "a1", "a2", "a3"})
                .filter(c -> c.startsWith("q")).min(String::compareToIgnoreCase);
        System.out.println(os.orElse("default"));

        List<String> ls2 = new ArrayList<String>();
        Optional.of("ab").ifPresent(ls2::add);
        Optional.of("xyz").ifPresent(ls2::add);
        System.out.println(ls2.size());

        System.out.println(ls2.parallelStream()
                .collect(Collectors.summarizingInt(String::length)));
        IntStream zeroToHundred = IntStream.rangeClosed(0, 100);
        //Stream<Integer> si = zeroToHundred.boxed(); // low efficiency
        String sentence1 = "\uD835\uDD46abc";
        sentence1.codePoints().forEach(System.out::println);
        System.out.println("-------------");
        zeroToHundred.mapToObj(c->String.format("->%X", c)).forEach(System.out::println);

        String[] init = {"One", "Two", "Three", "One", "Two", "Three"};
        List<String> list1 = new ArrayList(Arrays.asList(init));
        list1.removeAll(Collections.singleton("One"));
        Stream<String> ss2 = list1.stream();
        Map<Boolean, List<String>> mbs = ss2.collect(Collectors.partitioningBy(d -> d.length() > 3));
        System.out.println(mbs.size());
        Stream<String> ss3 = list1.stream();
        Map<String, Long> ms = ss3.collect(Collectors.groupingBy(Function.identity(), counting()));
        System.out.println(ms.size());
    }
}