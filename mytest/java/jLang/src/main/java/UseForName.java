import java.lang.reflect.Constructor;

class C1{
    static {
        System.out.println("static");
    }
    public C1(){System.out.println("default ctor");}
}
/**
 * @author liming.gong
 */
public class UseForName {
    public static void main(String[] args) {
        try {
            Class c = Class.forName("C");
            //执行static
            Constructor ctor = c.getConstructor();
            C1 obj = (C1) ctor.newInstance();

            ClassLoader l = ClassLoader.getSystemClassLoader();
            Class c2 = l.loadClass("C");
            Constructor ctor2 = c2.getConstructor();
            C1 obj2 = (C1) ctor2.newInstance();
            //第一次创建对象，才执行static
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}