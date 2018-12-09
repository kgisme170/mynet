import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * @author liming.gong
 */
public class ArrayIocTest {
    private List<String> list;
    private Map<String, String> map;
    private Set<String> set;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> l) {
        list = l;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> m) {
        map = m;
    }

    public Set<String> getSet() {
        return set;
    }

    public void setSet(Set<String> s) {
        set = s;
    }

    public void testData() {
        for (String s : list) {
            System.out.println(s);
        }
        for (String s : set) {
            System.out.println(s);
        }
        Set<String> keys = map.keySet();
        for (String s : keys) {
            System.out.println(s + ":" + map.get(s));
        }
    }
}