import java.util.concurrent.CountDownLatch;
/**
 * @author liming.glm
 */
public class UseCountDownLatch {
    public static void main(String [] args) {
        final CountDownLatch latch = new CountDownLatch(2);
        new Thread() {
            @Override
            public void run() {
                latch.countDown();
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                latch.countDown();
            }
        }.start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}