interface IMy{
    void f();
}
class father{
    void g(){}
}
public class testOverride extends father implements IMy{
    public void f() {
        System.out.println("f");
    }
    @Override
    void g(){
        System.out.println("child");
    }
    public static void main(String[] args){
        father f = new testOverride();
        f.g();
        ((IMy)f).f();
    }
}
