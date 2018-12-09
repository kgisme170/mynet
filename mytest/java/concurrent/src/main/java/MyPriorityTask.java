import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author liming.gong
 */
public class MyPriorityTask implements Runnable, Comparable<MyPriorityTask> {
    private int priority;
    private String name;

    public MyPriorityTask(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    private int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(MyPriorityTask o) {
        if (this.getPriority() < o.getPriority()) {
            return 1;
        }
        if (this.getPriority() > o.getPriority()) {
            return -1;
        }
        return 0;
    }

    @Override
    public void run() {
        System.out.printf("MyPriorityTask: %s 优先级是 :%d\n", name, priority);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("UseCountDownLatch").build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,
                2,
                1,
                TimeUnit.SECONDS,
                new PriorityBlockingQueue<>(),
                namedThreadFactory);
        final int nTask = 4;
        for (int i = 0; i < nTask; i++) {
            MyPriorityTask task = new MyPriorityTask("Task " + i, i);
            executor.execute(task);
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = nTask; i < nTask + nTask; i++) {
            MyPriorityTask task = new MyPriorityTask("Task " + i, i);
            executor.execute(task);
        }
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}