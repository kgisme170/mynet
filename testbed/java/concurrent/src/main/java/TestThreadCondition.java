import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author liming.gong
 */
public class TestThreadCondition {
    Lock reentrantLock = new ReentrantLock();
    Condition condition = reentrantLock.newCondition();
    AtomicBoolean atomicBoolean = new AtomicBoolean(true);

    public Thread tf1() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    reentrantLock.lock();
                    System.out.println("线程1加锁并 睡眠1s");
                    Thread.sleep(1000);
                    System.out.println("线程1结束睡眠1s");
                    while (atomicBoolean.get()) {
                        System.out.println("线程1释放锁等待Condition");
                        condition.await();
                        System.out.println("线程1等待结束");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    reentrantLock.unlock();
                }
            }
        };
        Thread t = new Thread(runnable);
        t.start();
        return t;
    }

    public Thread tf2() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                    System.out.println("线程2睡眠100ms，然后等待lock");
                    reentrantLock.lock();
                    System.out.println("线程2获得lock 睡眠0.5s");
                    Thread.sleep(500);
                    condition.signal();
                    System.out.println("线程2获得lock 睡眠0.5s");
                    Thread.sleep(500);
                    System.out.println("线程2获得lock 睡眠0.5s");
                    Thread.sleep(500);

                    atomicBoolean.compareAndSet(true, false);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    reentrantLock.unlock();
                }
            }
        };
        Thread t = new Thread(runnable);
        t.start();
        return t;
    }

    public static void main(String[] args) throws InterruptedException {
        TestThreadCondition t = new TestThreadCondition();
        Thread t1 = t.tf1();
        Thread t2 = t.tf2();
    }
}