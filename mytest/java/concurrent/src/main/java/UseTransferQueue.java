import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

/**
 * @author liming.gong
 */
public class UseTransferQueue {
    public static void main(String[] args) throws InterruptedException {
        TransferQueue<Integer> transferQueue = new LinkedTransferQueue<>();
        System.out.println("Step1");
        transferQueue.put(2);
        System.out.println("Step2");
        transferQueue.transfer(2);
        System.out.println("Step3");
    }
}