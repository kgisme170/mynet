/**
 * @author liming.gong
 */
public class UseThread {
    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("Begin");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("End");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final int iThread = 4;
        Thread[] t = new Thread[iThread];
        for (int i = 0; i < iThread; ++i) {
            t[i] = new MyThread();
            t[i].setDaemon(true);
            t[i].start();
        }
        Thread.sleep(3000);
    }
}