import org.junit.*;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;

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

    @Test
    public void TestConstructor() {
        List<String> stringList = Arrays.asList(new String[]{"xyz1", "abc", "1234"});
        List<Person> personList = stringList.stream().map(Person::new).collect(Collectors.toList());

        IntConsumer intConsumer = i -> System.out.println(i);
        for (int i = 0; i < 10; ++i) {
            intConsumer.accept(i);
        }
    }
}