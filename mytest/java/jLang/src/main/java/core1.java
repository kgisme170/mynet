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

interface myInterface {
    default void f() {
    }
}

interface youInterface {
}

interface IUserDao {
    void save();
}

class me implements myInterface, youInterface {

}

class interval<T extends Comparable & Serializable> implements Serializable {

}

class UserDao implements IUserDao {
    @Override
    public void save() {
        System.out.println("save");
    }
}

class StaticProxy implements IUserDao {
    IUserDao instance;

    public StaticProxy(IUserDao obj) {
        instance = obj;
    }

    @Override
    public void save() {
        System.out.println("Before");
        instance.save();
        System.out.println("After");
    }
}

class ProxyFactory {
    private Object target;

    public ProxyFactory(Object obj) {
        target = obj;
    }

    public Object getInstance() {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("Before");
                        //System.out.println("proxy="+proxy.toString());
                        Object o = method.invoke(target, args);
                        System.out.println("After");
                        return o;
                    }
                }
        );
    }
}

public class core1 {
    @SafeVarargs
    public static <T> void addAll(Collection<T> coll, T... ts) {

    }

    public static void main2(String[] args) throws Exception{
        List<Integer> listInteger =new ArrayList<Integer>();
        List<String> listString =new ArrayList<String>();
        printCollection(listInteger);
        printCollection(listString);
        Vector<? extends Number> x = new Vector<Integer>();//这是正确的
        Vector<? super Integer> y = new Vector<Number>();//这是正确的
    }
    public static void printCollection(Collection<?> collection){
        for(Object obj:collection){
            System.out.println(obj);
        }
    }
    public static void main(String[] args) {
        int i = 0b1000010 & 0b1000;
        Scanner s = new Scanner(System.in);
        System.out.printf("%tc\n", new Date());
        System.out.printf("%1$s\n", new Date());
        try {
            Scanner c = new Scanner(Paths.get("/tmp/file1.txt"));
        } catch (IOException e) {
            //e.printStackTrace();
        }
        BigInteger b = BigInteger.TEN;
        BigInteger b2 = BigInteger.valueOf(232432);
        System.out.println(b.add(b2));
        int[] a = new int[]{4, 1, 2, 3};
        int[] a2 = Arrays.copyOf(a, a.length);
        Arrays.sort(a2);
        System.out.println(Arrays.toString(a));
        //System.out.printf("Input:" + s.nextInt());

        System.out.println(LocalDate.now());
        int[][] a3 = (int[][]) Array.newInstance(a.getClass(), a.length);
        int[] a4 = (int[]) Array.newInstance(int.class, a.length);

        int x = 1;
        assert x < 0;
        IUserDao d = new UserDao();
        new StaticProxy(d).save();
        IUserDao dao = (IUserDao) new ProxyFactory(d).getInstance();
        System.out.println(dao.getClass());
        dao.save();
        Set<String> s1 = ConcurrentHashMap.newKeySet();
        System.out.println(s1);
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