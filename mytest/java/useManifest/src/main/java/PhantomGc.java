import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Field;

/**
 * @author liming.gong
 */
public class PhantomGc {
    static boolean run = true;

    public static void main(String[] args) {
        Integer i = new Integer(2);
        System.out.println(i.getClass());
        System.out.println(i.hashCode());
        final ReferenceQueue<Integer> referenceQueue = new ReferenceQueue<>();
        final PhantomReference<Integer> phantomReference = new PhantomReference<>(i, referenceQueue);
        new Thread() {
            @Override
            public void run() {
                while (run) {
                    Object obj = referenceQueue.poll();
                    if (obj != null) {
                        System.out.println("obj: " + obj.getClass());
                        System.out.println("obj: " + obj.hashCode());
                        try {
                            Field referent = Reference.class.getDeclaredField("referent");
                            referent.setAccessible(true);
                            /**
                             * 得到原始对象
                             */
                            Object result = referent.get(obj);
                            System.out.println("result: " + result.getClass());
                            System.out.println("result: " + result.hashCode());

                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();

        try {
            i = null;
            System.out.println("main 第1次睡眠");
            Thread.sleep(2000);
            System.gc();
            System.out.println("gc() + main 第2次睡眠");
            Thread.sleep(2000);
            run = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}