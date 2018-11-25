import java.io.*;

class noctor implements Externalizable { // Serializable {//Serializable不需要调用构造函数
    private String name;
    private int age;
    @Override
    public String toString(){
        return "name = " + name + ", age = " + age;
    }

    public noctor(){ // Externalizable 需要一个public默认构造函数
        name = "a";
        age = 1;
        System.out.println("默认构造");
    }
    public noctor(String n, int a){
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
        //Externalizable先调用默认构造函数，如果没有则报错
    }
}
interface myIt<T>{
    T f();
}
class myCls implements myIt<String>{
    @Override
    public String f(){return "hw";}
}
class myPair<K, V> {
    public myPair(K k, V v) {
        this.k = k;
        this.v = v;
    }

    private K k;
    private V v;

    @Override
    public String toString() {
        return "Key=" + k + ",Value=" + v;
    }

    public <T extends Comparable> void f(T t){
        System.out.println(t);
    }
}
class myGeneric{
    public <T> void f(){
        System.out.println("hello");
    }
    public static void f(myPair<? extends Object, ? extends Object> p){
        System.out.println(p);
    }
}
public class core3 {
    public static void main(String[] args) {
        try {
            noctor n = new noctor("abc", 12);
            System.out.println(n);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(out);

            oos.writeObject(n);
            System.out.println("反序列化之后,readObject");
            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(in);

            noctor n1 = (noctor) ois.readObject();
            System.out.println(n1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        myIt i = new myCls();
        System.out.println(i.f());
        myPair mp= new myPair<Integer, Long>(2, 3L);
        System.out.println(mp);
        mp.f("abc");
        myGeneric m = new myGeneric();
        m.f();
        myGeneric.f(new myPair("abc", new Integer(2)));
    }
}