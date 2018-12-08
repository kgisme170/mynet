import java.io.*;
class Base1 implements Serializable{
    protected String field = "xyz";
}

class Derived1 extends Base1 implements Serializable {
    private String name = "name";
    private int age = 12;

    private void readObject(ObjectInputStream ois) {
        System.out.println("readObject!!");
        try {
            field = (String) ois.readObject();
            name = (String) ois.readObject();
            age = ois.readInt();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeObject(ObjectOutputStream oos) {
        System.out.println("writeObject!!");
        try {
            oos.writeObject(field);
            oos.writeObject(name);
            oos.writeInt(age);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String toString() {
        return "field = " + field + ", name = " + name + ", age = " + age;
    }
}

/**
 * @author liming.gong
 */
public class UseSerializable {
    public static void main(String[] args) {
        try {
            File f = new File("file.dat");
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f));
            os.writeObject(new Base1());
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(f));
            Base1 c = (Base1) is.readObject();
            System.out.println(c);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}