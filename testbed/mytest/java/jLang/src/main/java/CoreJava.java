import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
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
        Stream.generate(new MySupplier()).limit(3).forEach(System.out::println);

        MyOuter out = new MyOuter();
        f1(out);
        f2(out);

        List<People> lp = new ArrayList<>(4);
        lp.add(new People());
        lp.add(new People());
        System.out.println(lp.size());
        Map<String, People> stringPeopleMap = lp.stream().collect(
                Collectors.toMap(People::getId, Function.identity(), (oldValue, newValue) -> oldValue, TreeMap::new)
        );
        stringPeopleMap.forEach((k, v) -> System.out.println(k));
        System.out.println(stringPeopleMap.getClass());

        City[] cities = {
                new City("Beijing", "Bj", 2000),
                new City("Shenzhen", "Guangdong", 2000),
                new City("Zhuhai", "Guangdong", 1500)};
        List<City> lc = new ArrayList(Arrays.asList(cities));
        Stream<City> slc = lc.stream();
        Map<String, Long> map = slc.collect(Collectors.groupingBy(City::getState, Collectors.counting()));
    }
}