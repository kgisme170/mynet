import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
/**
 * @author liming.gong
 */
public class UseClassLoader {
    public static void main(String [] args) {
        new MyClassLoader().findClass("BasicTypes");
    }
}
class MyClassLoader extends ClassLoader {
    public MyClassLoader() {
        System.out.println(this.getClass().getName());
        System.out.println(this.getClass().getTypeName());
    }

    @Override
    protected Class<?> findClass(String name) {
        Class<?> cl = null;
        try {
            byte[] classBytes = loadClassBytes(name);
            cl = defineClass(name, classBytes, 0, classBytes.length);
            if (cl == null) {
                throw new ClassNotFoundException(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cl;
    }

    private byte[] loadClassBytes(String name) throws IOException{
        String cname = name.replace('.', '/') + ".class";
        byte[] bytes = Files.readAllBytes(Paths.get(cname));
        return bytes;
    }
}