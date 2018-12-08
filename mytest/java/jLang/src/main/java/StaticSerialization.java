import java.io.*;

class MyClass01 implements Serializable {
    public static final MyClass01 m1 = new MyClass01(1);
    public static final MyClass01 m2 = new MyClass01(2);
    private static final long serialVersionUID = -1428732219630860994L;

    private MyClass01(int i) {
        mI = i;
    }

    private int mI = 0;

    public int getI() {
        return mI;
    }

    protected Object readResolve() throws ObjectStreamException {
        System.out.println("Serialization reflects into here");
        if (mI == 1) {
            return m1;
        } else if (mI == 2) {
            return m2;
        }
        throw new ObjectStreamException(){};
    }
}

/**
 * @author liming.gong
 */
public class StaticSerialization {
    public static void main(String[] args) {
        MyClass01 m1 = MyClass01.m1;
        try {
            ObjectOutputStream oss = new ObjectOutputStream(new FileOutputStream("MyClass01.data"));
            oss.writeObject(m1);
            oss.close();
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("MyClass01.data"));
            MyClass01 m2 = (MyClass01) ois.readObject();
            System.out.println(m2 == m1);
            System.out.println(m1.getI());
            System.out.println(m2.getI());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}