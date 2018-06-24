import java.util.concurrent.*;
public class testCountDownLatch{
    public static void main(String args[]){
        final CountDownLatch latch = new CountDownLatch(2);
        new Thread(){
            public void run(){
                latch.countDown();
            }
        }.start();
        new Thread(){
            public void run(){
                latch.countDown();
            }
        }.start();
        try{
            latch.await();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}