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
        int i = 0b1000010 & 0b1000;
        System.out.println(LocalDate.now());
        System.out.printf("%tc\n", new Date());
        System.out.printf("%1$s\n", new Date());
        try {
            Scanner c = new Scanner(Paths.get("/tmp/file1.txt"));
            System.out.println(c.next());
        } catch (IOException e) {
        }
        BigInteger b = BigInteger.TEN;
        BigInteger b2 = BigInteger.valueOf(232432);
        System.out.println(b.add(b2));
        int[] a = new int[]{4, 1, 2, 3};
        int[] a2 = Arrays.copyOf(a, a.length);
        Arrays.sort(a2);
        System.out.println(Arrays.toString(a));

        int[][] a3 = (int[][]) Array.newInstance(a.getClass(), a.length);
        int[] a4 = (int[]) Array.newInstance(int.class, a.length);

        int x = 1;
        assert x < 0;
        Set<String> s1 = ConcurrentHashMap.newKeySet();
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
    }
}