/**
 * @author liming.gong
 */
public class TestThreads {
    public static void main(String[] args) throws InterruptedException {
        class MyThread extends Thread {
            private int sleepMs;

            public MyThread(int sleepMs) {
                this.sleepMs = sleepMs;
            }

            @Override
            public void run() {
                try {
                    Thread.sleep(sleepMs);
                    System.out.println("id=" + Thread.currentThread() + ", sleep="+ sleepMs);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        MyThread t1 = new MyThread(1000);
        MyThread t2 = new MyThread(500);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}