import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.Executors.*;
import static java.util.concurrent.Executors.newScheduledThreadPool;

public class useThreadPool implements Callable<Integer>{
    private static AtomicInteger i = new AtomicInteger(0);

    @Override
    public Integer call() {
        try {
            System.out.println("开始");
            Thread.sleep(2000);
            System.out.println("结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i.incrementAndGet();
    }
    public static void testNormalThreadPool() {
        ExecutorService es1 = newCachedThreadPool();//newSingleThreadExecutor();//newFixedThreadPool(2);
        Callable<Integer> callable1 = new useThreadPool();
        Callable<Integer> callable2 = new useThreadPool();
        es1.submit(callable1);
        es1.submit(callable2);
        es1.shutdown();
    }
    public static void main(String[] args){
        AtomicInteger i = new AtomicInteger(0);
        ScheduledExecutorService es1 = newScheduledThreadPool(2);
        scheduleAtFixedRate(es1, 1000);
        scheduleAtFixedRate(es1, 6000);
        scheduleWithFixedDelay(es1, 6000);
        scheduleWithFixedDelay(es1, 6000);
    }
    private static void scheduleAtFixedRate(ScheduledExecutorService service, int sleepTime){
        service.scheduleWithFixedDelay(new sleepRunnable(sleepTime),1000,5000, TimeUnit.MILLISECONDS);
    }
    private static void scheduleWithFixedDelay(ScheduledExecutorService service, int sleepTime){
        service.scheduleWithFixedDelay(new sleepRunnable(sleepTime),1000,5000, TimeUnit.MILLISECONDS);
    }
}
class sleepRunnable implements Runnable {
    private int sleepTime;

    public sleepRunnable(int sleepTime) {
        this.sleepTime = sleepTime;
    }

    @Override
    public void run() {
        long start = new Date().getTime();
        System.out.println("sleepRunnable 开始执行时间:" +
                DateFormat.getTimeInstance().format(new Date()));
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = new Date().getTime();
        System.out.println("sleepRunnable 执行花费时间=" + (end - start) / 1000 + "m");
        System.out.println("sleepRunnable 执行完成时间："
                + DateFormat.getTimeInstance().format(new Date()));
        System.out.println("======================================");
    }
}