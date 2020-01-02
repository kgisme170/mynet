import java.util.concurrent.CountDownLatch;

/**
 * @author liming.gong
 */
public class WaitCountDownLatch {
    public static void main(String[] args) {
        /*
        CountDownLatch latch = new CountDownLatch(1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Before");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
                System.out.println("After");
            }
        }).start();
        try {
            latch.await();
            System.out.println("Main thread quits");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
    }
}