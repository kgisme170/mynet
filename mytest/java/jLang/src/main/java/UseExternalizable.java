import java.io.*;

class NoCtor implements Externalizable { // Serializable {//Serializable不需要调用构造函数
    private String name;
    private int age;

    @Override
    public String toString() {
        return "name = " + name + ", age = " + age;
    }

    public NoCtor() { // Externalizable 需要一个public默认构造函数
        name = "a";
        age = 1;
        System.out.println("默认构造");
    }

    public NoCtor(String n, int a) {
        name = n;
        age = a;
        System.out.println("有参构造");
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeInt(age);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        //name = (String)in.readObject();
        //age = in.readInt();
        //Externalizable先调用默认构造函数，如果没有则报异常
    }
}
/**
 * @author liming.gong
 */
public class UseExternalizable {
    public static void main(String[] args) {
        try {
            NoCtor n = new NoCtor("abc", 12);
            System.out.println(n);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(out);

            oos.writeObject(n);
            System.out.println("反序列化之后,readObject");
            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(in);

            NoCtor n1 = (NoCtor) ois.readObject();
            System.out.println(n1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}