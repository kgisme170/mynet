public class StaticMethodInnerClass {
    public static void main(String[] args) {
        f();
    }

    public static void f() {
        StaticMethodInnerClass s = new StaticMethodInnerClass();
        StaticMethodInnerClass.A a = s.new A();

        B b = new B();
        System.out.println("ok");
        b.f();
    }

    class A {

    }

    static class B {
        public void f() {
            System.out.println(++mI);
        }
    }

    private static int mI = 0;
}