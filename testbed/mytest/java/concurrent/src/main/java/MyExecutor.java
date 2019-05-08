import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author liming.gong
 */
public class MyExecutor extends ThreadPoolExecutor {
    private ConcurrentHashMap<String, Date> startTimes;

    public MyExecutor(int corePoolSize, int maximumPoolSize,
                      long keepAliveTime, TimeUnit unit,
                      BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                workQueue);
        startTimes = new ConcurrentHashMap<>();
    }

    @Override
    public void shutdown() {
        System.out.printf("MyExecutor: 准备关闭.\n");
        System.out.printf("MyExecutor: 已经执行的任务: %d\n", getCompletedTaskCount());
        System.out.printf("MyExecutor: 正在运行的任务: %d\n", getActiveCount());
        System.out.printf("MyExecutor: 挂起的任务: %d\n", getQueue().size());
        super.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        System.out.printf("MyExecutor_now: 准备立刻关闭.\n");
        System.out.printf("MyExecutor_now: 已经执行的任务: %d\n", getCompletedTaskCount());
        System.out.printf("MyExecutor_now: 正在运行的任务: %d\n", getActiveCount());
        System.out.printf("MyExecutor_now: 挂起的任务: %d\n", getQueue().size());
        return super.shutdownNow();
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        System.out.printf("MyExecutor: Task开始: %s :%s\n", t.getName(), r.hashCode());
        startTimes.put(String.valueOf(r.hashCode()), new Date());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        Future<?> result = (Future<?>) r;
        try {
            System.out.println("*********************************");
            Date startDate = startTimes.remove(String.valueOf(r.
                    hashCode()));
            Date finishDate = new Date();
            long diff = finishDate.getTime() - startDate.getTime();
            System.out.println("MyExecutor: 任务已经完成 Result: %s" + result.get() + ", 任务运行时间: %d" + diff);
            System.out.println("*********************************");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}


class SleepTwoSecondsTask implements Callable<String> {
    @Override
    public String call() throws Exception {
        TimeUnit.SECONDS.sleep(2);
        return new Date().toString();
    }

    public static void main(String[] args) {
        MyExecutor myExecutor = new MyExecutor(
                2,
                3,
                1000,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<Runnable>());
        List<Future<String>> results = new ArrayList<>();

        final int total = 10;
        final int partial = 5;
        for (int i = 0; i < total; i++) {
            SleepTwoSecondsTask task = new SleepTwoSecondsTask();
            Future<String> result = myExecutor.submit(task);
            results.add(result);
        }
        for (int i = 0; i < partial; i++) {
            try {
                String result = results.get(i).get();
                System.out.printf("已经完成的 Task %d :%s\n", i, result);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        myExecutor.shutdown();
        for (int i = partial; i < total; i++) {
            try {
                String result = results.get(i).get();
                System.out.printf("未完成的的 Task %d :%s\n", i, result);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        try {
            myExecutor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}