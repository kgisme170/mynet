import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liming.glm
 */
public class UseVolatile {
    final static int I_THREAD = 10;
    public static CountDownLatch l = new CountDownLatch(I_THREAD);

    public static void main(String[] args) throws InterruptedException {
        final AtomicInteger count = new AtomicInteger(0);
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("UseCountDownLatch").build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10,
                10,
                100,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1),
                namedThreadFactory);
        for (int i = 0; i < I_THREAD; ++i) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("run");
                    count.incrementAndGet();
                    System.out.println("end");
                    l.countDown();
                }
            });
        }
        l.await();
        executor.shutdown();
        System.out.println(count);
    }
}