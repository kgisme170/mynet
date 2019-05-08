import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author liming.gong
 */
public class UseCondition {
    public static void main(String[] args) {
        new UseCondition().transfer(1, 2, 3);
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