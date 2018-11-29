import java.util.*;

class Base{
    int mI;
}
class Derived extends Base{
    String name;
}
class TMy<T>{
    public void f(List<T> items){

    }
    public void g(List<? extends T> items){

    }
    public void h(List<? super T> items){

    }
}
public class useCovariant {
    public static void main(String[] args){
        List<Base> listB = new ArrayList<Base>();
        List<Derived> listD = new ArrayList<Derived>();

        TMy<Base> myB = new TMy<Base>();
        myB.f(listB);
        myB.g(listB);
        myB.h(listB);
        //myB.f(listD);//必须完全匹配
        myB.g(listD);//Consumer(dest)应该传入super类型
        //myB.h(listD);//

        TMy<Derived> my = new TMy<Derived>();
        //my.f(listB);//必须完全匹配
        //my.g(listB);//Provider(src)应该传入extend类型
        my.h(listB);
        my.f(listD);
        my.g(listD);
        my.h(listD);

    }
}
