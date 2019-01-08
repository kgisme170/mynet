import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UseHalfGenericType {
    public static void main(String[] args) {
        Map<String, ?> map = new HashMap<>(3);
        //map.put("abc", Optional.of(5));
        //map.put("kk", "xyz");
    }
}