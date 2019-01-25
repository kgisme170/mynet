class Super {
    static int taxi = 1729;
}
class Sub extends Super {
    static { System.out.print("Sub "); }
}

// interface里面的数据成员都是final属性
interface I {
    int i = 1, ii = Test.out("ii", 2);
}
interface J extends I {
    int j = Test.out("j", 3), jj = Test.out("jj", 4);
}
interface K extends J {
    int k = Test.out("k", 5);
}
class Test {
    public int h = 3;
    static int out(String s, int i) {
        System.out.println(s + "=" + i);
        return i;
    }
}
public class UseStatic {
    public static void main(String[] args) {
        System.out.println(Sub.taxi);
        System.out.println(J.i);
        System.out.println(K.j);
    }
}
