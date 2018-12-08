import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MapSet {
    public static void main(String[] args) {
        Map<String, Set<String>> map = new HashMap<>();
        Set<String> set = new HashSet<>();
        set.add("user1");
        set.add("user2");
        map.put("key1", set);

        Map<String, Set<String>> map2 = new HashMap<>();
        Map<String, Set<String>> map3 = new HashMap<>();
        map2.putAll(map);// 只拷贝了reference
        map3.putAll(map);
        map3.replaceAll((k, v) -> new HashSet<>(v));
        Map<String, Set<String>> map4 = map.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> new HashSet<>(e.getValue())));
        map.get("key1").add("user3");
        System.out.println(map2.get("key1").size()); // "map2"受到了影响
        System.out.println(map3.get("key1").size()); // "map3"没有受到影响
        System.out.println(map4.get("key1").size()); // "map4"没有受到影响
    }
}