import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;
/**
 * @author liming.glm
 */
public class UseLock {
    private static CuratorFramework client;
    private static String lockName = "/you/ok1/ok2/ok3";

    static {
        String hosts = "localhost:2181";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.newClient(hosts, retryPolicy);
        client.start();
    }

    public static void doWithLock() {
        InterProcessMutex lock = new InterProcessMutex(client, lockName);
        try {
            final int time = 3000
            if (lock.acquire(time, TimeUnit.SECONDS)) {
                System.out.println(Thread.currentThread().getName() + " hold lock");
                Thread.sleep(3000);
            } else {
                System.out.println(Thread.currentThread().getName() + " Cannot hold lock");
            }
        } catch (Exception e) {
            System.out.println(Thread.currentThread().getName() + ":" + e.getMessage());
        } finally {
            try {
                lock.release();
            } catch (Exception e) {
                System.out.println(Thread.currentThread().getName() + ":" + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            doWithLock();
        }, "t1");
        Thread t2 = new Thread(() -> {
            doWithLock();
        }, "t2");

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}