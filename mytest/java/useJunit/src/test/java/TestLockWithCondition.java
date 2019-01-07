import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author liming.gong
 */
public class TestLockWithCondition {
    static boolean flag = true;

    public static void main(String[] args) {
        Lock bankLock = new ReentrantLock();
        Condition sufficientFunds = bankLock.newCondition();
        try {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        bankLock.lock();
                        Thread.sleep(500);
                        System.out.println("signal main thread");
                        sufficientFunds.signalAll();
                        Thread.sleep(300);
                        flag = false;
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        bankLock.unlock();
                    }
                }
            };
            Thread t = new Thread(runnable);
            t.start();

            bankLock.lock();
            while (flag) {
                System.out.println("main enters while");
                sufficientFunds.await();
                System.out.println("main await ends");
            }
            System.out.println("main woke up" + Thread.currentThread());
            sufficientFunds.signalAll();
        System.out.println("end");
            t.join();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("finally unlock");
            bankLock.unlock();
        }
    }
}