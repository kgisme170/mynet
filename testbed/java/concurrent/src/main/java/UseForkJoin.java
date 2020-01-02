import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.function.DoublePredicate;

/**
 * @author liming.gong
 */
public class UseForkJoin extends RecursiveTask<Integer> {
    public static final int THRESHOLD = 1000;
    private double [] values;
    private int from;
    private int to;
    private DoublePredicate filter;

    public UseForkJoin(double[] values, int from, int to, DoublePredicate filter) {
        this.values = values;
        this.from = from;
        this.to = to;
        this.filter = filter;
    }

    @Override
    protected Integer compute() {
        if (to - from < THRESHOLD) {
            int count = 0;
            for (int i = from; i < to; ++i) {
                if (filter.test(values[i])) {
                    ++count;
                }
            }
            return count;
        } else {
            int mid = (from + to) / 2;
            UseForkJoin head = new UseForkJoin(values, from, mid, filter);
            UseForkJoin tail = new UseForkJoin(values, mid, to, filter);
            invokeAll(head, tail);
            return head.join() + tail.join();
        }
    }

    public static void main(String[] args) {
        final int size = 2000000;
        double[] numbers = new double[size];
        for (int i = 0; i < size; ++i) {
            numbers[i] = Math.random();
        }
        UseForkJoin useForkJoin = new UseForkJoin(numbers, 0, numbers.length, n -> n <= 0.5);
        new ForkJoinPool().invoke(useForkJoin);
        System.out.println(useForkJoin.join());
    }
}