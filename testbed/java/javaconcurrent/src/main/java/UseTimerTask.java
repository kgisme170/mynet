import java.util.Timer;
import java.util.TimerTask;
/**
 * @author liming.gong
 */
public class UseTimerTask {
    public static void main(String[] args) {
        // true means isDaemon;
        Timer timer = new Timer();
        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                try {
                    System.out.println("begin");
                    Thread.sleep(500);
                    System.out.println("end");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        // delay 是多长时间以后开始task
        timer.schedule(task1, 1000, 3000);

        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                int i = 0;
                int j = 2;
                System.out.println(j / i);
            }
        };

        timer.schedule(task2, 1000, 3000);
        try {
            Thread.sleep(3000);
        } catch (Exception ex) {
            timer.cancel(); // 只要有一个task失败了，所有task都中止
        }
    }
}