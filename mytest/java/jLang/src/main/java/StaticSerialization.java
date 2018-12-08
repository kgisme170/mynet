import java.io.*;

class MyClass01 implements Serializable {
    public static final MyClass01 M_1 = new MyClass01(1);
    public static final MyClass01 M_2 = new MyClass01(2);
    private static final long serialVersionUID = -1428732219630860994L;

    private MyClass01(int i) {
        mI = i;
    }

    private int mI = 0;

    public int getI() {
        return mI;
    }
    final int i1 = 1;
    final int i2 = 2;
    protected Object readResolve() throws ObjectStreamException {
        System.out.println("Serialization reflects into here");
        if (mI == i1) {
            return M_1;
        } else if (mI == i2) {
            return M_2;
        }
        throw new ObjectStreamException(){};
    }
}

/**
 * @author liming.gong
 */
public class StaticSerialization {
    public static void main(String[] args) {
        MyClass01 m1 = MyClass01.M_1;
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