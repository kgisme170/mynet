class MyRunnable implements Runnable {
    private Thread parent;

    public MyRunnable(Thread parent) {
        this.parent = parent;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println("Run异常");
            e.printStackTrace();
        }
        parent.interrupt();
    }
}

/**
 * @author liming.gong
 */
public class UseGc {
    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize");
        super.finalize();
    }

    public void f() {
    }

    public static void main(String[] args) {
        UseGc useGc = new UseGc();
        useGc.f();
        System.gc();
        Thread t = new Thread(new MyRunnable(Thread.currentThread()));
        try {
            t.start();
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("睡眠被中止 begin");
            e.printStackTrace();
            System.out.println("睡眠被中止 end");
        } finally {
            try {
                t.join();
            } catch (Exception e) {
                System.out.println("finalize");
                e.printStackTrace();
            }
        }
    }
}