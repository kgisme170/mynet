import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
/**
 * @author liming.gong
 */

public class LoadFactor {
    public static void main(String[] args) throws Exception {
        HashMap<Integer, Integer> m = new HashMap<>(13);
        final int cap = 1000;
        for (int i = 0; i < cap; ++i) {
            m.put(i, i);
        }
        Class<?> mapClass = m.getClass();
        Method capacity = mapClass.getDeclaredMethod("capacity");
        capacity.setAccessible(true);
        System.out.println("capacity: " + capacity.invoke(m));

        Field threshold = mapClass.getDeclaredField("threshold");
        threshold.setAccessible(true);
        System.out.println("threshold: " + threshold.get(m));
    }
}