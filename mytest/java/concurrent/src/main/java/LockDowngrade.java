import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;
/**
 * @author liming.gong
 */
public class LockDowngrade {
    public static void main(String[] args) throws InterruptedException {
        new ReadWriteLockTest().testLockDowngrading();
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

        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("LockDowngrade").build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10,
                10,
                100,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10),
                namedThreadFactory);
        final int iThread = 2;
        for (int i = 0; i < iThread; i++) {
            int finalI = i;
            executor.execute(new Runnable() {
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
            });
        }
        start.countDown();
        end.await();
        executor.shutdown();
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