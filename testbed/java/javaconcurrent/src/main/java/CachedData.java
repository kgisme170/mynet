import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author liming.gong
 */
public class CachedData {
    Object data;
    volatile boolean cacheValid;
    final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        new CachedData().cache();
    }

    public void cache() {
        //1. 上读锁
        rwl.readLock().lock();

        //2. 验证cacheValid
        if (!cacheValid) {
            rwl.readLock().unlock(); //3. 解除读锁
            rwl.writeLock().lock(); //4. 上写锁
            try {
                //5. 验证cacheValid
                if (!cacheValid) {
                    // data = ...
                    cacheValid = true;
                }
                rwl.readLock().lock(); //6. 上读锁
            } finally {
                rwl.writeLock().unlock(); //7. 解除写锁
            }
        }
        try {
            use(data);
        } finally {
            rwl.readLock().unlock();//8. 解除读锁
        }
    }

    public void use(Object object) {

    }
}