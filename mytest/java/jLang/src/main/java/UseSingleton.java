import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.*;
import java.lang.reflect.Constructor;

class S1 {
    public static final S1 instance = new S1();

    private S1() {
    }

    @Override
    public String toString() {
        return "S1";
    }
}

class S2 {
    private static final S2 instance = new S2();

    private S2() {
    }

    public static S2 getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return "S2";
    }
}

class BaseClass implements Serializable {
    int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

class S3  extends BaseClass{
    private static S3 instance = null;

    private S3() {
    }

    public static S3 getInstance() {
        if (instance == null) {
            synchronized (S3.class) {
                if (instance == null) {
                    instance = new S3();
                }
            }
        }
        return instance;
    }

    @Override
    public String toString() {
        return "S3";
    }
}

/**
 * enum 风格singleton
 */
enum S4 {
    instance;
    int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    /**
     * 相当于有一个这样的函数 */
    /*
    @Override
    protected Object readResolve() {
        return instance;
    }*/
}

public class UseSingleton {
    public static void main(String[] args) {
        System.out.println(S1.instance);
        System.out.println(S2.getInstance());
        System.out.println(S3.getInstance());
        // 无法阻止反射多个实例
        try {
            Constructor ctor = S1.instance.getClass().getDeclaredConstructor(new Class[0]);
            ctor.setAccessible(true);
            S1 s1_ = (S1)ctor.newInstance();
            if (s1_ == S1.instance) {
                System.out.println("Same");
            } else {
                System.out.println("New");
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 无法阻止反射创建多个实例
        try {
            S3 s3 = S3.getInstance();
            s3.setValue(1);
            ByteOutputStream bos = new ByteOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(s3);
            byte[] b = bos.getBytes();
            s3.setValue(3);

            ByteArrayInputStream bais = new ByteArrayInputStream(b);
            ObjectInputStream ois = new ObjectInputStream(bais);
            S3 s3_ = (S3)ois.readObject();
            System.out.println(s3.getValue());
            System.out.println(s3_.getValue());
            ois.close();
            bais.close();
            oos.close();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 能够阻止序列化/反序列化创建多个实例，因为有readResolve默认实现，返回同一个实例
        try {
            S4 s4 = S4.instance;
            s4.setValue(10);
            ByteOutputStream bos = new ByteOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(s4);
            byte[] b = bos.getBytes();
            s4.setValue(20);

            ByteArrayInputStream bais = new ByteArrayInputStream(b);
            ObjectInputStream ois = new ObjectInputStream(bais);
            S4 s4_ = (S4)ois.readObject();
            System.out.println(s4.getValue());
            System.out.println(s4_.getValue());
            ois.close();
            bais.close();
            oos.close();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}