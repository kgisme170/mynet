import java.lang.ref.PhantomReference;
import java.lang.ref.WeakReference;

/**
 * @author liming.gong
 */
public class ReferenceGc {
    public static void main(String [] args) {
        Integer integer = 2;
        WeakReference<Integer> wi = new WeakReference<>(integer);
        WeakReference<Integer> sr = new WeakReference<>(new Integer(3));
        System.out.println(wi.get());
        System.out.println(sr.get());
        System.gc();
        System.out.println("step 1 wi = " + wi.get());
        System.out.println("step 1 sr =: " + sr.get());
        integer = null;
        System.gc();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("step 1 wi = " + wi.get());
        System.out.println("step 1 sr =: " + sr.get());

        integer = 3;
        PhantomReference<Integer> integerPhantomReference = new PhantomReference<>(integer, null);
        System.out.println(integerPhantomReference.get());
    }
}
