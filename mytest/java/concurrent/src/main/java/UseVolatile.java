import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
class Singleton {
    private volatile static Singleton instance = null;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
    public void print(){System.out.println(Singleton.class.getName());}
}

/**
 * @author liming.glm
 */
public class UseVolatile {
    public static CountDownLatch l = new CountDownLatch(10);

    public static void main(String[] args) throws InterruptedException {
        final AtomicInteger count = new AtomicInteger(0);
        for (int i = 0; i < 10; ++i) {
            new Thread() {
                public void run() {
                    for (int c = 0; c < 100; ++c) {
                        count.incrementAndGet();
                    }
                    l.countDown();
                }
            }.start();
        }
        l.await();
        System.out.println(count);
        Singleton.getInstance().print();
    }
}