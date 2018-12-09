import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * @author liming.glm
 */
public class UseFuture {
    static class MyThread implements Callable<Integer> {
        private static AtomicInteger i = new AtomicInteger(0);

        @Override
        public Integer call() {
            try {
                System.out.println("开始UseFuture");
                Thread.sleep(2000);
                System.out.println("结束UseFuture");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return i.incrementAndGet();
        }
    }

    public static void main(String[] args) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("UseFuture").build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10,
                10,
                100,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1),
                namedThreadFactory);

        // FutureTask继承了Runnable和Future接口
        FutureTask<Integer> futureTask1 = new FutureTask<>(new MyThread());
        FutureTask<Integer> futureTask2 = new FutureTask<>(new MyThread());
        executor.execute(futureTask1);
        //主线程会等待所有start的子线程，除非子线程setDaemon

        executor.execute(futureTask2);
        executor.shutdown();

        //主线程会等待所有start的子线程，除非子线程setDaemon
        try {
            System.out.println(futureTask1.get());
            System.out.println(futureTask2.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        System.out.println("开始submit");
        Future f1 = pool.submit(new MyThread());
        Future f2 = pool.submit(new MyThread());
        System.out.println("--------------等待submit的对象");

        try {
            System.out.println(f1.get());
            System.out.println(f2.get());
            System.out.println("结束submit的对象");
        }catch(Exception e){
            e.printStackTrace();
        }
        pool.shutdown();
    }
}