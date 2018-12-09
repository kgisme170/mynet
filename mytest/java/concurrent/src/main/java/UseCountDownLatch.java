import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author liming.glm
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
                latch.countDown();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("end");
            }
        });
        try {
            latch.await();
            System.out.println("Before");
            executor.shutdown();
            System.out.println("After");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}