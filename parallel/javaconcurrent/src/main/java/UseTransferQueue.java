import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

/**
 * @author liming.gong
 */
public class UseTransferQueue {
    public static void main(String[] args) throws InterruptedException {
        TransferQueue<Integer> transferQueue = new LinkedTransferQueue<>();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println("子线程开始remove");
                    transferQueue.remove();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t = new Thread(runnable);
        t.start();
        System.out.println("Step1");
        System.out.println("Step2");
        transferQueue.transfer(2);
        System.out.println("Step3");
    }
}