import java.util.concurrent.*;

class ThreadFlag extends Thread {
    public volatile boolean flag = false;

    @Override
    public void run() {
        while (!flag) {
            System.out.println("running");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                if (Thread.interrupted()) {
                    System.out.println("中断");
                }
                e.printStackTrace();
                flag = true;
            }
        }
    }
}

class ThreadInfinite extends Thread {
    @Override
    public void run() {
        while (true) {
            if (Thread.interrupted()) {
                System.out.println("检查到中断");
                break;
            }
        }
    }
}

class MyThread2 implements Runnable{
    @Override
    public void run() {
        while (true) {
            try {
                if (Thread.interrupted()) {
                    System.out.println("终止");
                    throw new InterruptedException();
                }
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}

/**
 * @author liming.gong
 */
public class TerminateThread {
    public static void main(String[] args) throws Exception {
        ThreadFlag t = new ThreadFlag();
        t.start();
        try {
            Thread.sleep(2000);
            t.flag = true;
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ThreadFlag t2 = new ThreadFlag();
        t2.start();
        t2.interrupt();
        t2.join();
        Thread.sleep(500);
        System.out.println("------------------");
        ThreadInfinite loop = new ThreadInfinite();
        loop.start();
        loop.interrupt();

        MyThreadFactory threadFactory = new MyThreadFactory("MyThreadFactoryExecutor");
        /**
         * 取代ExecutorService executor = Executors.newCachedThreadPool(threadFactory)
         */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,
                2,
                1,
                TimeUnit.SECONDS,
                new PriorityBlockingQueue<>(),
                threadFactory);
        executor.submit(new MyThread2());
        executor.submit(new MyThread2());
        executor.submit(new MyThread2());
        executor.shutdownNow();
    }
}