class MyExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.printf("An exception has been captured\n");
        System.out.printf("Thread:%s\n", t.getName());
        System.out.printf("Exception: %s: %s:\n", e.getClass().getName(), e.getMessage());
        System.out.printf("Stack Trace:\n");
        System.out.printf("Thread status:%s\n", t.getState());
    }
}

/**
 * @author liming.gong
 */
public class TestUncaughtException {
    public static void main(String[] args) {
        Thread.currentThread().setUncaughtExceptionHandler(new MyExceptionHandler());
        int i = Integer.parseInt("ff");
    }
}