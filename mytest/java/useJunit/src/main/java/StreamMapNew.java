import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
class Person {
    private String name;
    public Person(String name) {
        this.name = name;
    }
    public String getName() { return name; }
}
/**
 * @author liming.gong
 */
public class StreamMapNew {
    public static void main(String[] args) {
        String[] array = new String[]{"xyz1", "abc", "1234"};
        List<String> stringList = Arrays.asList(array);
        List<Person> personList = stringList.stream().map(Person::new).collect(Collectors.toList());
        personList.forEach(x -> System.out.println(x.getName()));
    }
}