import java.lang.ref.PhantomReference;
import java.lang.ref.WeakReference;

/**
 * @author liming.gong
 */
public class ReferenceGcString {
    public static void main(String [] args) {
        String string = new String("abc");
        WeakReference<String> wi = new WeakReference<>(string);
        WeakReference<String> sr = new WeakReference<>(new String("another"));
        System.out.println(wi.get());
        System.out.println(sr.get());
        System.gc();
        System.out.println("step 1 wi = " + wi.get());
        System.out.println("step 1 sr =: " + sr.get());
        string = null;
        System.gc();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("step 1 wi = " + wi.get());
        System.out.println("step 1 sr =: " + sr.get());

        string = new String("xyz");
        PhantomReference<String> stringPhantomReference = new PhantomReference<>(string, null);
        System.out.println(stringPhantomReference.get());
    }
}
