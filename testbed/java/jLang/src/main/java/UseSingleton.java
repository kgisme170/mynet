import java.io.*;
import java.lang.reflect.Constructor;

class S1 {
    public static final S1 INSTANCE = new S1();

    private S1() {
    }

    @Override
    public String toString() {
        return "S1";
    }
}

class S2 {
    private static final S2 INSTANCE = new S2();

    private S2() {
    }

    public static S2 getInstance() {
        return INSTANCE;
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
    /**
     * Singleton声明
     */
    instance;

    /**
     * 测试
     */
    int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

/**
 * @author liming.gong
 */
public class UseSingleton {
    public static void main(String[] args) {
        System.out.println(S1.INSTANCE);
        System.out.println(S2.getInstance());
        System.out.println(S3.getInstance());
        // 无法阻止反射多个实例
        try {
            Constructor ctor = S1.INSTANCE.getClass().getDeclaredConstructor(new Class[0]);
            ctor.setAccessible(true);
            S1 s1 = (S1) ctor.newInstance();
            if (s1 == S1.INSTANCE) {
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
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(s3);
            byte[] b = bos.toByteArray();
            s3.setValue(3);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(b);
            ObjectInputStream ois = new ObjectInputStream(byteArrayInputStream);
            S3 s31 = (S3) ois.readObject();
            System.out.println(s3.getValue());
            System.out.println(s31.getValue());
            ois.close();
            byteArrayInputStream.close();
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
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(s4);
            byte[] b = bos.toByteArray();
            s4.setValue(20);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(b);
            ObjectInputStream ois = new ObjectInputStream(byteArrayInputStream);
            S4 s41 = (S4) ois.readObject();
            System.out.println(s4.getValue());
            System.out.println(s41.getValue());
            ois.close();
            byteArrayInputStream.close();
            oos.close();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}