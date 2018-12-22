import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;

class MySupplier implements Supplier<Integer> {
    public MySupplier() {
        System.out.println("new MySupplier");
    }
    @Override
    public Integer get() {
        return ++i;
    }
    private static int i = 0;
}

class MyMapper implements Function<String, String> {
    @Override
    public String apply(String s) {
        return s + "kkk";
    }
    public MyMapper() {
        System.out.println("new MyMapper");
    }
}

class MyInner {
    String a;
}

class MyOuter {
    MyInner inner;
}

class People {
    private String id = "ab";
    public String getId() { return id; }
}

class City {
    private String name;

    public String getName() {
        return name;
    }
    private String state;

    public String getState() {
        return state;
    }

    private int population;

    public int getPopulation() {
        return population;
    }

    public City(String n, String s, int p) {
        name = n;
        state = s;
        population = p;
    }
}
/**
 * @author liming.gong
 */
public class CoreJava {
    public static void f1(MyOuter outer) {
        if (outer != null) {
            MyInner in = outer.inner;
            if (in != null) {
                System.out.println(in.a);
            }
        }
    }

    public static void f2(MyOuter outer) {
        System.out.println(Optional.ofNullable(outer).map(o -> o.inner).map(i -> i.a));
    }

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
        Stream.generate(new MySupplier()).limit(3).forEach(System.out::println);
        System.out.println("--------------");
        Stream sf1 = Stream.of("a1", "b1").flatMap(str -> Arrays.stream(str.split("")));
        //System.out.println(sf1.count()); --> 4
        sf1.peek(str -> System.out.println(str));

        Optional<String> os = Arrays.stream(new String[]{"c0", "a1", "a2", "a3"})
                .filter(c -> c.startsWith("q")).min(String::compareToIgnoreCase);
        System.out.println(os.orElse("default"));
        //Optional<String> os2 = Arrays.stream(new Array).min(String::compareToIgnoreCase);
        List<String> ls2 = new ArrayList<String>();
        Optional.of("ab").ifPresent(ls2::add);
        Optional.of("xyz").ifPresent(ls2::add);
        System.out.println(ls2.size());

        System.out.println(ls2.parallelStream()
                .collect(Collectors.summarizingInt(String::length)));

        MyOuter out = new MyOuter();
        f1(out);
        f2(out);

        //System.out.println(Function<Integer>.identity())
        Arrays.stream(new String[]{"c0", "a1", "a2", "a3"})
                .map(new MyMapper()).forEach(System.out::println);
        List<People> lp = new ArrayList<>(4);
        lp.add(new People());
        lp.add(new People());
        System.out.println(lp.size());
        Map<String, People> stringPeopleMap = lp.stream().collect(
                Collectors.toMap(People::getId, Function.identity(), (oldValue, newValue) -> oldValue, TreeMap::new)
        );
        stringPeopleMap.forEach((k, v) -> System.out.println(k));
        System.out.println(stringPeopleMap.getClass());

        String[] init = {"One", "Two", "Three", "One", "Two", "Three"};
        List list1 = new ArrayList(Arrays.asList(init));
        list1.removeAll(Collections.singleton("One"));
        Stream<String> ss2 = list1.stream();
        Map<Boolean, List<String>> mbs = ss2.collect(Collectors.partitioningBy(d -> d.length() > 3));
        Stream<String> ss3 = list1.stream();
        //Map<String, long> msl = ss3.collect(Collectors.groupingBy(Function::identity, counting()));

        City[] cities = {
                new City("Beijing", "Bj", 2000),
                new City("Shenzhen", "Guangdong", 2000),
                new City("Zhuhai", "Guangdong", 1500)};
        List lc = new ArrayList(Arrays.asList(cities));
        Stream<String> slc = lc.stream();
        //Map<String, Long> map = slc.collect(Collectors.groupingBy(City::getState, Collectors.counting()));

        List<Integer> values = new ArrayList(Arrays.asList(new int[]{3, 4, 5, 6}));
        //values.stream().filter(x -> x > 3).reduce((x, y) -> x + y);
        //values.stream().reduce(0, (x, y) -> x + y);

        Arrays.stream(init).reduce((x, y) -> x + y);
        IntStream zeroToHundred = IntStream.rangeClosed(0, 100);
        //Stream<Integer> si = zeroToHundred.boxed(); // low efficiency
        String sentence1 = "\uD835\uDD46abc";
        sentence1.codePoints().forEach(System.out::println);
        System.out.println("-------------");
        zeroToHundred.mapToObj(c->String.format("->%X", c)).forEach(System.out::println);
    }
}