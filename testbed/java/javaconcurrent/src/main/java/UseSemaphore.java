import java.util.concurrent.*;
/**
 * @author liming.gong
 */
public class UseSemaphore {
    static Semaphore s = new Semaphore(2);

    public static void main(String[] args) throws Exception {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("子进程睡眠开始");
                    Thread.sleep(1000);
                    System.out.println("子进程睡眠结束");
                    s.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t = new Thread(runnable);
        t.start();
        System.out.println("step1");
        s.acquire();
        System.out.println("step2");
        s.acquire();
        System.out.println("step3");
        s.acquire();
        System.out.println("OK了");
    }
}