import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.Date;

class MyThread extends Thread {
    private Date creationDate;
    private Date startDate;
    private Date finishDate;

    public MyThread(Runnable target, String name) {
        super(target, name);
        setCreationDate();
    }

    @Override
    public void run() {
        setStartDate();
        super.run();
        setFinishDate();
    }

    public void setCreationDate() {
        creationDate = new Date();
    }

    public void setStartDate() {
        startDate = new Date();
    }

    public void setFinishDate() {
        finishDate = new Date();
    }

    public long getExecutionTime() {
        return finishDate.getTime() - startDate.getTime();
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(getName());
        buffer.append(": ");
        buffer.append(" 创建时间: ");
        buffer.append(creationDate);
        buffer.append(" : 运行时间: ");
        buffer.append(getExecutionTime());
        buffer.append(" 毫秒.");
        return buffer.toString();
    }
}

/**
 * @author liming.gong
 */
public class MyThreadFactory implements ThreadFactory {
    private int counter;
    private String prefix;

    public MyThreadFactory(String prefix) {
        counter = 1;
        this.prefix = prefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        MyThread myThread = new MyThread(r, prefix + "-" + counter);
        counter++;
        return myThread;
    }

    public static void main(String[] args) throws Exception {
        MyThreadFactory myFactory = new MyThreadFactory("MyThreadFactory");
        Thread thread = myFactory.newThread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        thread.join();
        System.out.printf("Main: Thread information.\n");
        System.out.printf("%s\n", thread);
        System.out.printf("Main: End of the example.\n");
    }
}