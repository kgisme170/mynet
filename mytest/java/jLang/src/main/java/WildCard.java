import java.util.ArrayList;
import java.util.List;
/**
 * @author liming.gong
 */
public class WildCard {
    List<?> myList = null;
    WildCard(List<String> l) {
        myList = l;
    }
    public static void main(String[] args) {
        List<?> listWildcard = new ArrayList<String>();

        List<String> list = new ArrayList<>();
        list.add("ok");
        list.add("123");
        List<?> list1 = list;
        // list1.add("xyz");//compile error
        List list2 = list;
        list2.add(123);

        // ClassCastException
        for (String s : list) {
            System.out.println(s);
        }
    }
}