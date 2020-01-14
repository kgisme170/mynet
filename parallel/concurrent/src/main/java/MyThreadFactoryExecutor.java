import java.util.concurrent.*;

/**
 * @author liming.gong
 */
public class MyThreadFactoryExecutor {
    public static void main(String[] args) {
        MyThreadFactory threadFactory = new MyThreadFactory("MyThreadFactoryExecutor");
        /**
         * 取代ExecutorService executor = Executors.newCachedThreadPool(threadFactory)
         */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,
                2,
                1,
                TimeUnit.SECONDS,
                new PriorityBlockingQueue<>(),
                threadFactory);
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Begin");
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}