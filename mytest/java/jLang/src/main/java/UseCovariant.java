import java.io.Serializable;
import java.util.*;
class Interval<T extends Comparable & Serializable> implements Serializable { }

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
public class UseCovariant {
    public static void printCollection(Collection<?> collection){
        for(Object obj:collection){
            System.out.println(obj);
        }
    }
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

        System.out.println();
        List<Integer> listInteger =new ArrayList<Integer>();
        List<String> listString =new ArrayList<String>();
        printCollection(listInteger);
        printCollection(listString);
        Vector<? extends Number> x = new Vector<Integer>();//这是正确的
        Vector<? super Integer> y = new Vector<Number>();//这是正确的
    }
}
