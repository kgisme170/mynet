import java.util.concurrent.atomic.AtomicInteger;

public class useThreadLocal {
    private static final AtomicInteger nextId = new AtomicInteger(0);
    public static ThreadLocal<String> idHolder = new ThreadLocal<String>() {
        @Override
        protected String initialValue() {
            return nextId.getAndIncrement() + "Id";
        }
    };

    public static String get() {
        return idHolder.get();
    }

    public static void main(String [] args){
        for(int i=0;i<5;++i) {
            final Thread t = new Thread(){
                @Override
                public void run(){
                    System.out.println(useThreadLocal.get());
                }
            };
            t.start();
        }
    }
}