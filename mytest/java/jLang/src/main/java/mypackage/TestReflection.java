package mypackage;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
/**
 * @author liming.gong
 */
public class TestReflection {
    /** 去除Object基类的方法
     *
     * @param
     * @return
     */
    private List<Method> getMethods(Class c) {
        Method[] cMethods = c.getMethods();
        Method[] oMethods = Object.class.getMethods();
        HashSet<Method> setC = new HashSet<Method>(Arrays.asList(cMethods));
        HashSet<Method> setO = new HashSet<Method>(Arrays.asList(oMethods));
        setC.removeAll(setO);
        return new ArrayList<>(setC);
    }

    private List<Method> getDeclaredMethods(Class c) {
        Method[] cMethods = c.getDeclaredMethods();
        Method[] oMethods = Object.class.getDeclaredMethods();
        HashSet<Method> setC = new HashSet<Method>(Arrays.asList(cMethods));
        HashSet<Method> setO = new HashSet<Method>(Arrays.asList(oMethods));
        setC.removeAll(setO);
        return new ArrayList<>(setC);
    }

    private void printMethods(List<Method> mList, String msg) {
        System.out.println(msg);
        for (Method m : mList) {
            System.out.println(m.getName() + "_" + m.getParameterCount());
            Class[] parameterTypes = m.getParameterTypes();
            for (Class c : parameterTypes) {
                System.out.println(c.getName());
            }
        }
    }

    public TestReflection() {
    }

    public void CheckClass(Class c) {
        System.out.println("=====================");
        System.out.println("类名" + c.getName());
        System.out.println(c.getPackage().getName());
        int modifiers = c.getModifiers();
        System.out.println(Modifier.isProtected(modifiers));
        printMethods(getMethods(c), "getMethod===========================");
        printMethods(getDeclaredMethods(c), "getDeclaredMethods===============");
    }

    class Base {
        public void baseF() {
        }

        protected void baseH() {
        }

        private int baseG() {
            return 0;
        }
    }

    class My extends Base {
        public void f() {
        }

        private int g() {
            return 1;
        }

        public My(String _) {
        }
    }

    public class You {
        public You(String s) {
        }

        public void f(String s, int i) {
            System.out.println(i + 100);
        }
    }

    public static void main(String[] args) throws NoSuchMethodException {
        TestReflection test = new TestReflection();
        try {
            //公有函数，包括父类的
            test.CheckClass(String.class);
            //本类的，包括私有和公有
            test.CheckClass(You.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Constructor[] constructors = You.class.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] parameterTypes = constructor.getParameterTypes();
            for (Class c : parameterTypes) {
                //print java.lang.String
                System.out.println(c.getName());
            }
        }
        Constructor constructor =
                You.class.getConstructor(TestReflection.class, String.class);
        try {
            You y = (You)constructor.newInstance(test, "xzy");
            System.out.println("ok");
            y.f("xyz",2);
        }catch(Exception e){
            e.printStackTrace();
        }
        Method m1 = You.class.getMethod("f", new Class[]{String.class, int.class});
        Method m2 = You.class.getMethod("f", String.class, int.class);
        You y = new TestReflection().new You("abc");
        try {
            m2.invoke(y, "xyz", 3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}