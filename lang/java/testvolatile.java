import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
public class testvolatile{
    public static CountDownLatch l = new CountDownLatch(10);
    public static void main(String[] args)throws InterruptedException{
        AtomicInteger count = new AtomicInteger(0);
        for(int i=0;i<10;++i){
            new Thread(){
                public void run(){
                    for(int c=0;c<100;++c){
                        count.incrementAndGet();
                    }
                    l.countDown();
                }
            }.start();
        }
        l.await();
        System.out.print(count);
    }
}