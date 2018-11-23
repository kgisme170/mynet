import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class useLock {
    private RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
    private InterProcessMutex mutex;

    public useLock() {
        String hosts = "localhost:2181";
        String lockName = "/you/ok1/ok2/ok3";
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(hosts, retryPolicy);
        curatorFramework.start();
        mutex = new InterProcessMutex(curatorFramework, lockName);
    }

    public void TryLock() {
        try {
            mutex.acquire();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
!
    public static void main(String[] args){
        useLock lock = new useLock();
        lock.TryLock();
    }
}