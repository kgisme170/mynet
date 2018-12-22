import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
class mySupplier implements Supplier<Integer> {
    @Override
    public Integer get() {
        return ++i;
    }
    private static int i = 0;
}

public class coreJava {
    public static void main(String [] args) {
        List<String> list = Arrays.asList(new String[]{"xyz", "abc"});
        List<String> list2 = list.parallelStream()
                .filter(w -> w.length() > 2).collect(Collectors.toList());
        System.out.println(list2);

        Object [] aS = list.toArray();
        System.out.println(aS);

        Stream s = Stream.of("a1", "b1");

        s.forEach(System.out::println);
        Stream.generate(()->"abc").limit(10).forEach(System.out::println);
        Stream<BigInteger> sb = Stream.iterate(BigInteger.ONE, n->n.add(BigInteger.ONE));
        sb.limit(10).forEach(System.out::println);

        Stream.Builder<String> bd = Stream.builder();
        bd.accept("abc");
        bd.accept("xyz");
        bd.add("kkk");
        Stream<String> ss = bd.build().parallel();
        ss.forEach(System.out::println);

        Arrays.stream(new String[]{"a0", "a1", "a2", "a3"}, 1,3).forEach(System.out::println);
        Stream.generate(new mySupplier()).limit(3).forEach(System.out::println);
        System.out.println("--------------");
        Stream sf1 = Stream.of("a1", "b1").flatMap(str->Arrays.stream(str.split("")));
        //System.out.println(sf1.count()); --> 4
        sf1.peek(str -> System.out.println(str));

        Optional<String> os = Arrays.stream(new String[]{"c0", "a1", "a2", "a3"}).filter(c->c.startsWith("q")).min(String::compareToIgnoreCase);
        System.out.println(os.orElse("default"));
        //Optional<String> os2 = Arrays.stream(new Array).min(String::compareToIgnoreCase);
        List<String> ls2 = new ArrayList<String>();
        Optional.of("ab").ifPresent(ls2::add);
        System.out.println(ls2.size());
    }
}
