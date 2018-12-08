import java.io.*;

class my implements Serializable {
    public static final my m1 = new my(1);
    public static final my m2 = new my(2);
    private static final long serialVersionUID = -1428732219630860994L;

    private my(int i) {
        mI = i;
    }

    private int mI = 0;

    public int getI() {
        return mI;
    }

    protected Object readResolve() throws ObjectStreamException {
        System.out.println("Serialization reflects into here");
        if (mI == 1) return m1;
        if (mI == 2) return m2;
        throw new ObjectStreamException(){};
    }
}
public class StaticSerialization {
    public static void main(String[] args) {
        my m1 = my.m1;
        try {
            ObjectOutputStream oss = new ObjectOutputStream(new FileOutputStream("my.data"));
            oss.writeObject(m1);
            oss.close();
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("my.data"));
            my m2 = (my) ois.readObject();
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