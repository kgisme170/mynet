import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author liming.gong
 */
public class UseCountDownLatch {
    public static void main(String [] args) {
        final CountDownLatch latch = new CountDownLatch(1);
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("UseCountDownLatch").build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10,
                10,
                100,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1),
                namedThreadFactory);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("run");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
                System.out.println("end");
            }
        });
        try {
            latch.await();
            System.out.println("After latch await");
            executor.shutdown();
            System.out.println("Main thread quits");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}