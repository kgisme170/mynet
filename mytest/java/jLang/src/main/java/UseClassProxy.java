import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
interface Person{
    void sing();
    void dance();
}
class Star implements Person{

    @Override
    public void sing() {
        System.out.println("sing");
    }

    @Override
    public void dance() {
        System.out.println("dance");
    }
}
class ProxyFactory {
    private Person target;

    public ProxyFactory(Person obj) {
        target = obj;
    }

    public Person getInstance() {
        return (Person) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("Before");
                        Object o = method.invoke(target, args);
                        System.out.println("After");
                        return o;
                    }
                }
        );
    }
}

public class UseClassProxy {
    public static void main(String[] args) {
        Person s = new Star();
        ProxyFactory pf = new ProxyFactory(s);
        Person o = pf.getInstance();
        o.sing();
        o.dance();
    }
}