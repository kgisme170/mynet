import java.util.Random;
import java.util.concurrent.*;

import static java.util.concurrent.Executors.newCachedThreadPool;
/**
 * @author liming.glm
 */
public class UseCompletion {
    public static void main(String[] args) {
        UseCompletion uc = new UseCompletion();
        uc.f1();
        uc.f2();
    }

    public void f1() {
        ExecutorService exec = newCachedThreadPool();
        BlockingDeque<Future<Integer>> queue = new LinkedBlockingDeque<>();
        for (int i = 0; i < 10; ++i) {
            queue.add(exec.submit(new MyTask()));
        }
        int sum = 0;
        try {
            for (int i = 0; i < queue.size(); ++i) {
                sum += queue.take().get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(sum);
        exec.shutdown();
    }

    public void f2() {
        ExecutorService exec = newCachedThreadPool();
        CompletionService<Integer> queue = new ExecutorCompletionService<>(exec);
        for (int i = 0; i < 10; ++i) {
            queue.submit(new MyTask());
        }
        int sum = 0;
        try {
            for (int i = 0; i < 10; ++i) {
                sum += queue.take().get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(sum);
        exec.shutdown();
    }

    public class MyTask implements Callable<Integer> {
        final Random rand = new Random();

        @Override
        public Integer call() throws Exception {
            int i = rand.nextInt(10);
            int j = rand.nextInt(10);
            int sum = i * j;
            System.out.print(sum + "\t");
            return sum;

        }
    }
}