import java.lang.ref.PhantomReference;
import java.lang.ref.WeakReference;

class MyObj {
    int mI;
    MyObj(int i) { mI = i; }
}
/**
 * @author liming.gong
 */
public class ReferenceGcMyObj {
    public static void main(String[] args) {
        MyObj myObj = new MyObj(1);
        WeakReference<MyObj> wi = new WeakReference<>(myObj);
        WeakReference<MyObj> sr = new WeakReference<>(new MyObj(2));
        System.out.println(wi.get());
        System.out.println(sr.get());
        System.gc();
        System.out.println("step 1 wi = " + wi.get());
        System.out.println("step 1 sr =: " + sr.get());
        myObj = null;
        System.gc();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("step 1 wi = " + wi.get());
        System.out.println("step 1 sr =: " + sr.get());

        myObj = new MyObj(3);
        PhantomReference<MyObj> myObjPhantomReference = new PhantomReference<>(myObj, null);
        System.out.println(myObjPhantomReference.get());
    }
}