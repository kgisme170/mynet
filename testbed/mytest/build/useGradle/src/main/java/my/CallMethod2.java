package my;
/**
 * @author liming.gong
 */
public class CallMethod2 {
    int method(String arg) {
        return 1;
    }

    int method(Object arg) {
        return 2;
    }

    void f() {
        Object o = "Object";
        int result = method(o);
        System.out.println(result);
    }

    public static void main(String[] args) {
        new CallMethod2().f();
    }
}