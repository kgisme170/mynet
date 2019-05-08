class OurRunnable implements Runnable {
    private int id;

    public OurRunnable(int id) {
        this.id = id;
    }
    final int loop = 50000000;
    @Override
    public void run() {
        try {
            int sum = 0;
            for (int i = 0; i < loop; ++i) {
                sum *= i;
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
        final int loop = 5;
        for (int i = 0; i < loop; ++i) {
            t[i] = new Thread(new OurRunnable(i));
            t[i].setPriority(Thread.MIN_PRIORITY);
        }
        for (int i = loop; i < loop + loop; ++i) {
            t[i] = new Thread(new OurRunnable(i));
            t[i].setPriority(Thread.MAX_PRIORITY);
        }
        for (int i = 0; i < loop + loop; ++i) {
            t[i].start();
        }
        for (int i = 0; i < loop + loop; ++i) {
            t[i].join();
        }
    }
}