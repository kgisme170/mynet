import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
interface IPerson {
    /**
     * java.lang.reflect.Proxy 只能代理 interface
     * @return void
     */
    void say();
}
class Person2 implements IPerson{
    private String name;
    public Person2(String name) {
        this.name = name;
    }
    @Override
    public void say() {
        System.out.println("Person:" + name);
    }
}
class MyHandler implements InvocationHandler {
    private Object object;
    public MyHandler(Object o) {
        object = o;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("方法名=" + method.getName());
        System.out.println("args=" + args);
        return method.invoke(object, args);
    }
}

/**
 * @author liming.gong
 */
public class TestProxy {
    public static void main(String[] args) {
        Person2 p = new Person2("myName");
        InvocationHandler invocationHandler = new MyHandler(p);
        IPerson obj = (IPerson) Proxy.newProxyInstance(
                p.getClass().getClassLoader(),
                p.getClass().getInterfaces(),
                invocationHandler);
        obj.say();
    }
}