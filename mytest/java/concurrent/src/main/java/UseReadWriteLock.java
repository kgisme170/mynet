import java.util.concurrent.locks.*;
import java.util.concurrent.locks.ReentrantReadWriteLock.*;

/**
 * @author liming.gong
 */
public class UseReadWriteLock {
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private ReadLock readLock = lock.readLock();
    private WriteLock writeLock = lock.writeLock();

    public Thread readThread() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    System.out.println("尝试获取读锁");
                    readLock.lock();
                    System.out.println("readLock获得成功");
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    readLock.unlock();
                }
            }
        };
        Thread t = new Thread(runnable);
        t.start();
        return t;
    }

    public Thread writeThread() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    writeLock.lock();
                    System.out.println("writeLock获得成功");
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    writeLock.unlock();
                }
            }
        };
        Thread t = new Thread(runnable);
        t.start();
        return t;
    }

    public static void main(String[] args) {
        UseReadWriteLock useReadWriteLock = new UseReadWriteLock();
        Thread t1 = useReadWriteLock.readThread();
        Thread t2 = useReadWriteLock.writeThread();
    }
}