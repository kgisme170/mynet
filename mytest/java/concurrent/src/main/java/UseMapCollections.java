import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liming.gong
 */

public class UseMapCollections {
    public static void main(String[] args) {
        Map<String, Integer> map = new ConcurrentHashMap<>(3);
        map.put("xyz", 3);
        map.put("abc", 9);
        map.put("ok", 7);
        String result = ((ConcurrentHashMap<String, Integer>) map).search(2, (k, v) -> v > 5 ? k : null);
        System.out.println(result);
        ((ConcurrentHashMap<String, Integer>) map).forEach(2, (k, v) -> k + "->" + v, System.out::println);
        int sum = ((ConcurrentHashMap<String, Integer>) map).reduceValues(2, Integer::sum);
        System.out.println(sum);
    }
}
