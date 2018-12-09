import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
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
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("UseThreadLocal").build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10,
                10,
                100,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1),
                namedThreadFactory);

        for (int i = 0; i < iThread; ++i) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(UseThreadLocal.get());
                }
            });
        }
        executor.shutdown();
    }
}