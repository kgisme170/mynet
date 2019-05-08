class Super {
    static int taxi = 1729;
}
class Sub extends Super {
    static { System.out.print("Sub "); }
}

/**
 * interface里面的数据成员都是final属性
  */
interface InterfaceI {
    int I = 1, II = Test.out("II", 2);
}
interface InterfaceJ extends InterfaceI {
    int J = Test.out("J", 3), JJ = Test.out("jj", 4);
}
interface InterfaceK extends InterfaceJ {
    int K = Test.out("K", 5);
}
class Test {
    static int out(String s, int i) {
        System.out.println(s + "=" + i);
        return i;
    }
}

/**
 * @author liming.gong
 */
public class UseStatic {
    public static void main(String[] args) {
        System.out.println(Sub.taxi);
        System.out.println(InterfaceJ.I);
        System.out.println(InterfaceK.J);
    }
}
