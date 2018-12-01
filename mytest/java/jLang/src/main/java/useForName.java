import java.lang.reflect.Constructor;

class C{
    static {
        System.out.println("static");
    }
    public C(){System.out.println("default ctor");}
}
public class useForName {
    public static void main(String[] args) {
        try {
            Class c = Class.forName("C");
            Constructor ctor = c.getConstructor();
            C obj = (C) ctor.newInstance();

            ClassLoader l = ClassLoader.getSystemClassLoader();
            Class c2 = l.loadClass("C");
            Constructor ctor2 = c2.getConstructor();
            C obj2 = (C) ctor2.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}