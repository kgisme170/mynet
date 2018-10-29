import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class annoTest {
    public static void main(String [] args) {
        List<Integer> uses = new ArrayList<Integer>();
        Collections.addAll(uses, 1, 2, 3, 4);
        for (Method m : annoUse.class.getDeclaredMethods()) {
            annoDefinition d = m.getAnnotation(annoDefinition.class);
            if (d != null) {
                System.out.println(d.id() + ":" + d.description());
                uses.remove(new Integer(d.id()));
            }
        }
        for (int i : uses) {
            System.out.println("方法" + i + "没有找到");
        }
    }
}
