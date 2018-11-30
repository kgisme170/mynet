import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class useFuture {
    static class myThread implements Callable<Integer> {
        private static AtomicInteger i = new AtomicInteger(0);

        @Override
        public Integer call() {
            try {
                System.out.println("开始");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return i.incrementAndGet();
        }
    }

    public static void main(String[] args) {
        // FutureTask继承了Runnable和Future接口
        FutureTask<Integer> futureTask1 = new FutureTask<>(new myThread());
        FutureTask<Integer> futureTask2 = new FutureTask<>(new myThread());
        new Thread(futureTask1, "有返回值的thread").start();//主线程会等待所有start的子线程，除非子线程setDaemon
        new Thread(futureTask2, "有返回值的thread").start();//主线程会等待所有start的子线程，除非子线程setDaemon
        try {
            System.out.println(futureTask1.get());
            System.out.println(futureTask2.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        System.out.println("开始submit");
        Future f1 = pool.submit(new myThread());
        Future f2 = pool.submit(new myThread());
        System.out.println("--------------等待submit的对象");

        try {
            System.out.println(f1.get());
            System.out.println(f2.get());
            System.out.println("结束submit的对象");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}