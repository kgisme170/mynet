/**
 * @author liming.gong
 */
public class TestThread {
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("睡眠中断");
                }
            }
        };
        try {
            Thread t = new Thread(runnable);
            System.out.println(t.getState());
            t.start();
            System.out.println(t.getState());
            Thread.sleep(500);
            System.out.println(t.getState());
            t.interrupt();
            System.out.println(t.getState());
            Thread.sleep(100);
            System.out.println(t.getState());
            t.join();
            System.out.println(t.getState());
        } catch (Exception e) {
            System.out.println("线程中断");
            e.printStackTrace();
        }
    }
}