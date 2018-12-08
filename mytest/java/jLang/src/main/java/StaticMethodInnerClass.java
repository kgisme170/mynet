/**
 * @author liming.gong
 */
public class StaticMethodInnerClass {
    public static void main(String[] args) {
        f();
    }

    public static void f() {
        StaticMethodInnerClass s = new StaticMethodInnerClass();
        A1 a = s.new A1();

        B1 b = new B1();
        System.out.println("ok");
        b.f();
    }

    class A1 {

    }

    static class B1 {
        public void f() {
            System.out.println(++mI);
        }
    }

    private static int mI = 0;
}