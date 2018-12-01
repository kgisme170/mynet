import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.*;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class core1 {
    @SafeVarargs
    public static <T> void addAll(Collection<T> coll, T... ts) {

    }

    public static void main(String[] args) {
    }

    private int Accounts[] = new int[]{};
    private void DoTransfer(int from, int to, double amount){}
    public void Transfer(int from, int to, double amount) {
        ReentrantLock locker = new ReentrantLock();
        Condition sufficientFunds = locker.newCondition();//条件对象，
        locker.lock();
        try {
            while (Accounts[from] < amount) {
                sufficientFunds.await();
                //等待有足够的钱
            }
            DoTransfer(from, to, amount);
            sufficientFunds.signalAll();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            locker.unlock();
        }

        ReadWriteLock l = new ReentrantReadWriteLock(); //从读写锁创建锁
        l.writeLock().lock();

        //jdk 1.8 Channel???????
    }
}