import java.util.concurrent.atomic.AtomicInteger;
/**
 * @author liming.glm
 */
public class UseThreadLocal {
    private static final AtomicInteger NEXT_ID = new AtomicInteger(0);
    public static ThreadLocal<String> idHolder = new ThreadLocal<String>() {
        @Override
        protected String initialValue() {
            return NEXT_ID.getAndIncrement() + "Id";
        }
    };

    public static String get() {
        return idHolder.get();
    }

    public static void main(String[] args) {
        final int iThread = 5;
        for (int i = 0; i < iThread; ++i) {
            final Thread t = new Thread() {
                @Override
                public void run() {
                    System.out.println(UseThreadLocal.get());
                }
            };
            t.start();
        }
    }
}