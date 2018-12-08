interface IMy{
    /**
     * 测试
     */
    void f();
}
class Father {
    void g(){}
}

/**
 * @author liming.gong
 */
public class TestOverride extends Father implements IMy{
    @Override
    public void f() {
        System.out.println("f");
    }
    @Override
    void g(){
        System.out.println("child");
    }
    public static void main(String[] args){
        Father f = new TestOverride();
        f.g();
        ((IMy)f).f();
    }
}
