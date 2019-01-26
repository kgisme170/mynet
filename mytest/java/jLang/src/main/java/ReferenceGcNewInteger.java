import java.lang.ref.PhantomReference;
import java.lang.ref.WeakReference;

/**
 * @author liming.gong
 */
public class ReferenceGcNewInteger {
    public static void main(String [] args) {
        System.out.println(Integer.valueOf(127).equals(Integer.valueOf(127)));
        System.out.println(Integer.valueOf(128).equals(Integer.valueOf(128)));
        /**
         * 只有new Integer()才能保证总是新建一个对象，而不是javac生成的cache对象
         */
        Integer integer = new Integer(2);
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

        integer = new Integer(3);
        PhantomReference<Integer> integerPhantomReference = new PhantomReference<>(integer, null);
        System.out.println(integerPhantomReference.get());
    }
}
