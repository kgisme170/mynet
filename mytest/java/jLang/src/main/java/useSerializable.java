import java.io.*;

class c1 extends base implements Serializable {
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

    public String toString() {
        return "field = " + field + ", name = " + name + ", age = " + age;
    }
}

public class useSerializable {
    public static void main(String[] args) {
        try {
            File f = new File("file.dat");
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f));
            os.writeObject(new c1());
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(f));
            c1 c = (c1) is.readObject();
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