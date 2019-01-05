import org.junit.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;

interface IMy {
    default void f1() {}
    default void f2() {}
    default void f3() {}
    static void sf() {
        System.out.println("f1, f2");
    }
}

class MyImpl implements IMy {
    @Override
    public void f3() {
        f1();
        f2();
        MyImpl.super.f3();//
        System.out.println("f3");
    }
}

class Person {
    private String name;
    public Person(String name) {
        this.name = name;
    }
    public void say() {
        System.out.println("Person:" + name);
    }
}

public class test02 {
    class My {
        int i = 1;

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof My) {
                My m = (My) obj;
                return i == m.i;
            } else {
                return false;
            }
        }
    }

    class You {
        int i = 1;
    }

    @Test
    public void TestMyEqual() {
        My m1 = new My();
        My m2 = new My();
        Assert.assertEquals(m1, m2);
    }

    @Test
    public void TestYouEqual() {
        You m1 = new You();
        You m2 = new You();
        Assert.assertNotEquals(m1, m2);
    }

    class LengthCompare implements Comparator<String> {
        public int compare(String s1, String s2) {
            return s1.length() - s2.length();
        }
    }

    @Test
    public void TestCompare() {
        String[] str = new String[]{"xyz1", "abc", "1234"};

        Arrays.sort(str, new LengthCompare());
        for (String s : str) {
            System.out.println(s);
        }
        Arrays.sort(str, Comparator.comparingInt(String::length));
        for (String s : str) {
            System.out.println(s);
        }
        Comparator<String> comparator = (first, second) -> first.length() - second.length();
        Arrays.sort(str, comparator);
    }

    @Test
    public void TestConstructor() {
        List<String> stringList = Arrays.asList(new String[]{"xyz1", "abc", "1234"});
        List<Person> personList = stringList.stream().map(Person::new).collect(Collectors.toList());

        IntConsumer intConsumer = i -> System.out.println(i);
        for (int i = 0; i < 10; ++i) {
            intConsumer.accept(i);
        }
    }

    class Him {
        private int i;
        private int j;
        private String s;

        public Him(int _i, int _j, String _s) {
            i = _i;
            j = _j;
            s = _s;
        }

        public int getI() {
            return i;
        }

        public int getJ() {
            return j;
        }

        public String getS() {
            return s;
        }
    }

    @Test
    public void TestComparator() {
        Him h1 = new Him(30, 2, "h1");
        Him h2 = new Him(20, 2, "h2");
        Him h3 = new Him(30, 3, "h3");

        ArrayList<Him> himArrayList = new ArrayList<>();
        himArrayList.add(h1);
        himArrayList.add(h2);
        himArrayList.add(h3);
        himArrayList.sort(
                Comparator.comparing(Him::getI).thenComparing(Him::getJ)
                        .thenComparing(Him::getS, (s1, s2) -> s1.length() - s2.length()));
    }
}