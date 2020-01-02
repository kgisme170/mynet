class Base2 {
    void print(String... args) {
        System.out.println("Base2.....test");
    }
}

class Sub2 extends Base2 {
    @Override
    void print(String[] args) {
        System.out.println("Sub2.....test");
    }
}
/**
 * @author liming.gong
 */
public class UseVarArgs {
    public static void main(String[] args) {
        Base2 base = new Sub2();
        base.print("hello");
        base.print("hello", "hi");

        // 不转型
        Sub2 sub = new Sub2();
        //sub.print("hello");
    }
}