class MyRunnable implements Runnable {
    private int id;

    public MyRunnable(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {
            int sum = 0;
            for (int i = 0; i < 50000000; ++i) {
                sum *= i;
                //Thread.sleep(1000);
            }
            System.out.println("线程号" + id);
        } catch (Exception e) {
            System.out.println("睡眠中断" + id);
        }
    }
}

/**
 * @author liming.gong
 */
public class TestPriority {
    public static void main(String[] args) throws InterruptedException {
        Thread[] t = new Thread[10];
        for (int i = 0; i < 5; ++i) {
            t[i] = new Thread(new MyRunnable(i));
            t[i].setPriority(Thread.MIN_PRIORITY);
        }
        for (int i = 5; i < 10; ++i) {
            t[i] = new Thread(new MyRunnable(i));
            t[i].setPriority(Thread.MAX_PRIORITY);
        }
        for (int i = 0; i < 10; ++i) {
            t[i].start();
        }
        for (int i = 0; i < 10; ++i) {
            t[i].join();
        }
    }
}