import java.util.*;

public class UseHalfGenericType {
    static <T> void reAdd(Map<String, T> map) {
        T value = map.get("123");
        map.put("456", value);
    }

    public static void main(String[] args) {
        Map<String, ?> m = new HashMap<>(3);
        reAdd(m);
        Map<String, ? super Object> map = new HashMap<>(3);//OK
        map.put("abc", Optional.of(5));
        map.put("kk", "xyz");
        map.forEach((k, v) -> System.out.println(k + "\t" + v));

        List<?> l = new ArrayList<String>();
        List<? extends Exception> l2 = new ArrayList<RuntimeException>();
        List<? super Exception> l3 = new ArrayList<Object>();
    }
}