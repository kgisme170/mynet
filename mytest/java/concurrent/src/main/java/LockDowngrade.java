import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;
/**
 * @author liming.gong
 */
public class LockDowngrade {
    @SafeVarargs
    public static <T> void addAll(Collection<T> coll, T... ts) {

    }

    public static void main(String[] args) throws InterruptedException {
        new LockDowngrade().transfer(1, 2, 3);
        new CachedData().processCachedData();
        new ReadWriteLockTest().testLockDowngrading();
    }

    private int[] accounts = new int[]{3, 4, 5};

    private void doTransfer(int from, int to, double amount) {
    }

    public void transfer(int from, int to, double amount) {
        ReentrantLock locker = new ReentrantLock();
        Condition sufficientFunds = locker.newCondition();
        //条件对象，
        locker.lock();
        try {
            while (accounts[from] < amount) {
                sufficientFunds.await();
            }
            doTransfer(from, to, amount);
            sufficientFunds.signalAll();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            locker.unlock();
        }

        ReadWriteLock l = new ReentrantReadWriteLock();
        //从读写锁创建锁
        l.writeLock().lock();
    }
}

class CachedData {
    Object data;
    volatile boolean cacheValid;
    final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    void processCachedData() {
        //1. 上读锁
        rwl.readLock().lock();

        //2. 验证cacheValid
        if (!cacheValid) {
            // Must release read lock before acquiring write lock
            rwl.readLock().unlock(); //3. 解除读锁
            rwl.writeLock().lock(); //4. 上写锁
            try {
                // Recheck state because another thread might have
                // acquired write lock and changed state before we did.
                //5. 验证cacheValid
                if (!cacheValid) {
                    // data = ...
                    cacheValid = true;
                }
                // Downgrade by acquiring read lock before releasing write lock
                rwl.readLock().lock(); //6. 上读锁
            } finally {
                rwl.writeLock().unlock(); // Unlock write, still hold read //7. 解除写锁
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

class ReadWriteLockTest {
    private volatile boolean cacheValid = false;
    private int currentValue = 0;
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock readLock = lock.readLock();
    private Lock writeLock = lock.writeLock();

    /**
     * 测试用例
     *
     * @throws InterruptedException 没有降级时
     *                              after sleep 0 seconds, excute thread-0
     *                              thread-0 has updated!
     *                              after sleep 1 seconds, excute thread-1
     *                              thread-1 has updated!
     *                              thread-0: 1
     *                              thread-1: 1
     *                              <p>
     *                              有降级时
     *                              after sleep 0 seconds, excute thread-0
     *                              thread-0 has updated!
     *                              after sleep 1 seconds, excute thread-1
     *                              thread-0: 0
     *                              thread-1 has updated!
     *                              thread-1: 1
     */
    public void testLockDowngrading() throws InterruptedException {
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(2);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10, 10, 100,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10));
        final int iThread = 2;
        for (int i = 0; i < iThread; i++) {
            int finalI = i;
            executor.execute(new Thread(new Runnable() {
                @Override
                public void run() {
                    Thread.currentThread().setName("thread-" + finalI);
                    try {
                        start.await();
                        TimeUnit.SECONDS.sleep(finalI);
                        System.out.println("after sleep " + finalI + " seconds, excute " + Thread.currentThread().getName());
                        cacheValid = false;
                        processCachedDataDownGrading(finalI);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        end.countDown();
                    }
                }
            }));
        }
        start.countDown();
        end.await();
    }

    /**
     * 锁降级过程
     *
     * @param num
     */
    private void processCachedDataDownGrading(int num) {
        readLock.lock();
        if (!cacheValid) {
            //必须先释放写锁
            readLock.unlock();
            writeLock.lock();
            try {
                //在更新数据之前做二次检查
                if (!cacheValid) {
                    System.out.println(Thread.currentThread().getName() + " has updated!");
                    //将数据更新为和线程值相同，以便验证数据
                    currentValue = num;
                    cacheValid = true;
                    readLock.lock();
                }
            } finally {
                writeLock.unlock();
            }
        }

        try {
            //模拟5秒的处理时间，并打印出当前值，在这个过程中cacheValid可能被其他线程修改，锁降级保证其他线程写锁被阻塞，数据不被改变
            TimeUnit.SECONDS.sleep(5);
            System.out.println(Thread.currentThread().getName() + ": " + currentValue);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock.getReadHoldCount() > 0) {
                readLock.unlock();
            }
        }
    }

    /**
     * 无锁降级的过程
     *
     * @param num
     */
    private void processCachedData(int num) {
        readLock.lock();
        if (!cacheValid) {
            readLock.unlock();
            writeLock.lock();
            try {
                if (!cacheValid) {
                    System.out.println(Thread.currentThread().getName() + " has updated!");
                    currentValue = num;
                    cacheValid = true;
                }
            } finally {
                writeLock.unlock();
            }
        }
        try {
            //模拟5秒的处理时间，并打印出当前值，在这个过程中cacheValid可能被其他线程修改，无锁降级过程，其他线程此时可能获取写锁，并更改书数据，导致后面的数据错误
            TimeUnit.SECONDS.sleep(5);
            System.out.println(Thread.currentThread().getName() + ": " + currentValue);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock.getReadHoldCount() > 0) {
                readLock.unlock();
            }
        }
    }
}