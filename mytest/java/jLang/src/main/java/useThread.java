public class useThread {
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
        Thread[] t = new Thread[4];
        for (int i = 0; i < 4; ++i) {
            t[i] = new MyThread();
            t[i].setDaemon(true);
            t[i].start();
        }
        Thread.sleep(3000);
    }
}