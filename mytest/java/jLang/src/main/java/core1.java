import javafx.util.Pair;

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

enum Status {
    SCUUESS("1", "成功"), FAILED("2", "失败");

    public String value;
    public String desc;

    private Status(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}

interface myit {
    default void f() {
    }
}

interface youit {
}

interface IUserDao {
    void save();
}

class me implements myit, youit {

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

    public static void print1(Pair<? extends me, String> p) {
    }

    public static void print2(Pair<? super me, String> p) {
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
        String h = "hello";
        String h1 = "你好哦!";
        try {
            System.out.println(new String(h.getBytes("UTF-8"), "UTF-8").getBytes("UTF-16").length);
            System.out.println(new String(h.getBytes("UTF-16"), "UTF-16").length());
            System.out.println(new String(h1.getBytes("UTF-8"), "UTF-8").length());
            System.out.println(new String(h1.getBytes("UTF-16"), "UTF-16").length());
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        System.out.println();
        System.out.println(h.codePointCount(0, h.length()));
        System.out.println(h.length());
        System.out.println(Status.SCUUESS.getValue());
        System.out.println(Status.SCUUESS.getDesc());
        System.out.println(Status.FAILED.getValue());
        System.out.println(Status.FAILED.getDesc());

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

        Map<Status, String> m = new HashMap<Status, String>();
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
        try {
            Method m2 = useFinal.class.getMethod("test01", Map.class, List.class, String.class);
            Type[] t = m2.getGenericParameterTypes();
            System.out.println(t.length);
            for (Type paramType : t) {
                System.out.println("#" + paramType);
                if (paramType instanceof ParameterizedType) {
                    //获取泛型中的具体信息
                    Type[] genericTypes = ((ParameterizedType) paramType).getActualTypeArguments();
                    for (Type genericType : genericTypes) {
                        System.out.println("泛型类型参数：" + genericType);
                    }
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        Set<String> s1 = ConcurrentHashMap.newKeySet();
        System.out.println(h.getBytes().length);

        String h2 = "hello实验";
        System.out.println(h2.codePointCount(0,h.length())); //7
        System.out.println(h2.getBytes().length); //11
        System.out.println("水".getBytes().length);//3
    }

    public void test01(Map<String, String> map, List<String> list, String s) {
        System.out.println("Demo.test01()");
    }

    public Map<Integer, String> test02() {
        System.out.println("Demo.test02()");
        return null;
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