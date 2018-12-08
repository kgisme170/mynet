interface MyIt<T> {
    /**
     * 测试
     */
    T f();
}
class MyCls implements MyIt<String> {
    @Override
    public String f() {
        return "hw";
    }
}
class MyPair<K, V> {
    public MyPair(K k, V v) {
        this.k = k;
        this.v = v;
    }

    private K k;
    private V v;

    @Override
    public String toString() {
        return "Key=" + k + ",Value=" + v;
    }

    public <T extends Comparable> void f(T t) {
        System.out.println(t);
    }
}

class MyGeneric {
    public <T> void f() {
        System.out.println("hello");
    }

    public static void f(MyPair<? extends Object, ? extends Object> p) {
        System.out.println(p);
    }
}

/**
 * @author liming.gong
 */
public class UseGeneric {
    public static void main(String[] args) {
        MyIt i = new MyCls();
        System.out.println(i.f());
        MyPair mp = new MyPair<Integer, Long>(2, 3L);
        System.out.println(mp);
        mp.f("abc");
        MyGeneric m = new MyGeneric();
        m.f();
        MyGeneric.f(new MyPair("abc", new Integer(2)));
    }
}