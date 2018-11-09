package mypackage;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class testReflection {
    // 去除Object基类的方法
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
        }
    }

    public testReflection() {
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

        public My(String _){}
    }

    class You{
        public You(String s){}
    }

    public static void main(String[] args)  throws NoSuchMethodException {
        try {
            testReflection test = new testReflection();
            test.CheckClass(String.class);//公有函数，包括父类的
            test.CheckClass(My.class);//本类的，包括私有和公有
        } catch (Exception e) {
            e.printStackTrace();
        }
        Constructor[] constructors = You.class.getConstructors();
        for(Constructor constructor: constructors){
            Class[] parameterTypes = constructor.getParameterTypes();
            for(Class c: parameterTypes){
                System.out.println(c.getName());//print java.lang.String
            }
        }
        Constructor constructor =
                You.class.getConstructor(String.class);//NoSuchMethodException?
    }
}