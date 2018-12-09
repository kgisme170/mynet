import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author liming.gong
 */
public class MyThreadFactoryExecutor {
    public static void main(String[] args) {
        MyThreadFactory threadFactory = new MyThreadFactory("MyThreadFactory");
        ExecutorService executor = Executors.newCachedThreadPool(threadFactory);
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