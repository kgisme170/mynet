import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingByConcurrent;
import static java.util.stream.Collectors.toSet;
/**
 * @author liming.glm
 */
public class UseStream {
    public static void main(String[] args) {
        arrayStream();
        listStream();
        streamGenerate();
        mapStream();
        numericStream();
        localeStream();

        Optional<String> optional = Arrays.stream("xyzabc".split("")).max(String::compareToIgnoreCase);
        IntSummaryStatistics iss = Arrays.stream(new String[]{"abc","zed","hello"}).collect(Collectors.summarizingInt(String::length));
        System.out.println(iss.getSum() + ", " + iss.getAverage() + ", " + iss.getCount() + ", " + iss.getMax());
    }

    public static void arrayStream() {
        Arrays.stream("abc".split("")).forEach(System.out::println);
        Arrays.stream("abc".split("")).peek(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        }).count();
        Stream stream = Arrays.asList(new String[]{"a", "b", "c"}).stream();
        stream.forEach(System.out::println);
        System.out.println(Arrays.stream("xyzabc".split("")).findFirst());
        System.out.println(Arrays.stream(new int[]{2,4,6,8}).allMatch(x->x%2==0));
    }

    public static void listStream() {
        //List<String> a = s3.collect(Collectors.toList());//已经是terminate状态
        List<Optional<String>> c = new ArrayList<>();
        c.add(Optional.of("abc"));
        c.add(Optional.ofNullable(null));
        c.add(Optional.of("xyz"));
        c = c.stream().map(o -> o.map(String::toUpperCase)).collect(Collectors.toList());
        System.out.println(c);
        List<String> ls = new ArrayList<>();
        ls.add("abc");
        ls.add("xyz");
        List list3 = ls.stream().collect(Collectors.toList());
        Set s = c.stream().collect(Collectors.toSet());
        List<Optional<String>> l = c.stream().collect(Collectors.toCollection(ArrayList::new));

        List<Integer> n = Arrays.asList(2, 3, 4);
        n.stream().map(x -> x * x).forEach(System.out::print);
        System.out.println();
    }

    public static void streamGenerate() {
        Integer[] ia = Stream.of(1,3,5,7).toArray(Integer[]::new);
        Stream<List<Integer>> is = Stream.of(
                Arrays.asList(3, 5, 7),
                Arrays.asList(2, 4, 6),
                Arrays.asList(0, 1, 8)
        );
        Stream<Integer> os = is.flatMap(list -> list.stream());
        os.forEach(System.out::print);
        System.out.println();
        System.out.println(os);

        Stream.of(new Integer[]{1, 2, 3, 4, 5}).filter(x -> x % 2 == 0).forEach(System.out::println);
        System.out.println(Stream.of(new Integer[]{1, 2, 3, 4, 5}).reduce(2, Integer::sum));
        System.out.println("--------------");
        Stream.of(new Integer[]{1, 22, 3, 14, 5})
                .sorted((a, b) -> (b.compareTo(a)))
                .forEach(System.out::println);
        System.out.println();
        Stream.generate(new Random()::nextInt).limit(8).forEach(System.out::println);
        System.out.println();
        Stream.generate(() -> (int) (System.nanoTime() % 100)).limit(8).forEach(System.out::println);

        Stream.generate(new Supplier<Integer>() {
            @Override
            public Integer get() {
                return 10;
            }
        }).limit(8).forEach(System.out::println);

        Stream<String> s2 = Stream.generate(() -> "echo");
        String[] words = new String[]{"Hello", "World"};
        Stream<String> s3 = Stream.of(words)
                .flatMap(str -> Arrays.stream(str.split("")))
                .distinct();
        s3.forEach(System.out::print);
    }

    public static void mapStream() {
        class My {
            private String name;
            private int age;

            public My(String n, int a) {
                name = n;
                age = a;
            }

            public String getName() {
                return name;
            }

            public int getAge() {
                return age;
            }
        }
        My m1 = new My("my", 23);
        My m2 = new My("my", 23);
        My m3 = new My("myx", 13);
        My m4 = new My("myx", 23);
        List<My> listMy = Arrays.asList(m1, m2, m3, m4);
        Map<String, List<My>> map = listMy
                .stream()
                .collect(Collectors.groupingBy(My::getName));
        map.forEach((k, v) -> System.out.println(v.size()));
        Map<Boolean, List<My>> map2 = listMy
                .stream()
                .collect(Collectors.partitioningBy(x -> x.getAge() < 18));
        map2.forEach((k, v) -> System.out.println(v.size()));

        Map<Integer, Long> m = Arrays.asList(new String[]{"abc","zed","hello"}).parallelStream()
                .collect(groupingByConcurrent(String::length, counting()));
        System.out.println(m);
    }

    public static void numericStream() {
        //0-1之间的小数
        Stream<Double> sd = Stream.generate(Math::random);
        final int iLimit = 10;
        for (Double db : sd.limit(iLimit).collect(Collectors.toSet())) {
            System.out.println(db);
        }

        Stream<Integer> si = Stream.iterate(1, n1 -> n1 + 1);
        IntStream intStream = IntStream.iterate(1, n2 -> n2 + 1);
        IntStream.range(1, 10).skip(3).limit(3).forEach(System.out::println);
        IntStream.rangeClosed(1, 4).forEach(System.out::print);
        System.out.println();
        IntStream iStream = IntStream.range(1, 4);
        List list1 = iStream.boxed().collect(Collectors.toList());
    }

    public static void localeStream() {
        Locale locale = new Locale("en", "US", "WIN");
        System.out.println("step1");
        System.out.println(locale.getLanguage());
        {
            Stream<Locale> sl = Stream.of(Locale.getAvailableLocales());
            Map<String, List<Locale>> map1 = sl.collect(Collectors.groupingBy(Locale::getCountry));
            for (Map.Entry<String, List<Locale>> e : map1.entrySet()) {
                System.out.println(e.getKey());
                System.out.println(e.getValue());
            }
        }
        System.out.println("step2");
        {
            Stream<Locale> sl = Stream.of(Locale.getAvailableLocales());
            Map<String, List<Locale>> map3 = sl.collect(Collectors.groupingBy(Locale::getCountry));
        }
        System.out.println("step3");
        {
            Stream<Locale> sl = Stream.of(Locale.getAvailableLocales());
            Map<Boolean, List<Locale>> map4 = sl.collect(Collectors.partitioningBy(lo -> "en".equals(lo.getLanguage())));
        }
        System.out.println("step4");
        System.out.println("step3");
        {
            Stream<Locale> sl = Stream.of(Locale.getAvailableLocales());
            Map<String, Set<Locale>> map5 = sl.collect(Collectors.groupingBy(Locale::getCountry, toSet()));
        }
    }
}