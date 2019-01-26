import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * @author liming.gong
 */
public class TestReference {
    private static final int KB = 1024;

    static class MegaByte {
        byte[] b;

        MegaByte() {
            b = new byte[KB * KB];
        }

        void f() {
            System.out.println(b[10]);
        }
    }

    static MegaByte m = new MegaByte();

    /**
     * 内存溢出之前进行回收
     */
    static SoftReference<MegaByte> softReference = new SoftReference<>(m);
    static WeakReference<MegaByte> weakReference = new WeakReference<>(m);
    static ReferenceQueue<MegaByte> referenceQueue = new ReferenceQueue<>();
    static PhantomReference<MegaByte> phantomReference = new PhantomReference<>(m, referenceQueue);

    public static void main(String[] args) {
        Integer integer = 2;
        WeakReference<Integer> sr = new WeakReference<>(integer);
        System.out.println(sr.get());
        System.gc();                //通知JVM的gc进行垃圾回收
        System.out.println("step 1: " + sr.get());
        integer = null;
        System.gc();
        /**
         * 弱引用总是被回收
         */
        System.out.println("step 2: " + sr.get());

        integer = 3;
        PhantomReference<Integer> integerPhantomReference = new PhantomReference<>(integer, null);
        System.out.println(integerPhantomReference.get());
        System.exit(1);

        Thread t = new Thread() {
            @Override
            public void run() {
                System.out.println("start");
                try {
                    while (true) {
                        String s1 = softReference.get() == null ? "null" : "soft";
                        String s2 = weakReference.get() == null ? "null" : "weak";
                        String s3 = phantomReference.get() == null ? "null" : "phantom";
                        System.out.println(s1 + ":" + s2 + ":" + s3);
                        Thread.sleep(500);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        m = null;
        MegaByte[] megaBytes = new MegaByte[KB];
        for (int i = 0; i < KB; ++i) {
            try {
                Thread.sleep(500);
                megaBytes[i] = new MegaByte();
                System.gc();
            } catch (OutOfMemoryError e) {
                System.out.println("内存不足 i=" + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}