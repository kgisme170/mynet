interface myIt<T> {
    T f();
}
class myCls implements myIt<String> {
    @Override
    public String f() {
        return "hw";
    }
}
class myPair<K, V> {
    public myPair(K k, V v) {
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
class myGeneric {
    public <T> void f() {
        System.out.println("hello");
    }

    public static void f(myPair<? extends Object, ? extends Object> p) {
        System.out.println(p);
    }
}
public class useGeneric {
    public static void main(String[] args) {
        myIt i = new myCls();
        System.out.println(i.f());
        myPair mp = new myPair<Integer, Long>(2, 3L);
        System.out.println(mp);
        mp.f("abc");
        myGeneric m = new myGeneric();
        m.f();
        myGeneric.f(new myPair("abc", new Integer(2)));
    }
}