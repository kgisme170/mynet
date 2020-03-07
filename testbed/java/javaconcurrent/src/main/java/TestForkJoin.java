import java.util.concurrent.*;
public class TestForkJoin extends RecursiveTask<Long> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final long THRESHOLD = 10000;
    private static final long LOOPEND = 100000000;
    private final long start;
    private final long end;

    public TestForkJoin(final long start, final long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long sum = 0;
        final boolean canComplute = (end - start) <= THRESHOLD;
        if (canComplute) {
            for (long i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            final long middle = (start + end) / 2;
            final TestForkJoin left = new TestForkJoin(start, middle);
            final TestForkJoin right = new TestForkJoin(middle + 1, end);
            invokeAll(left, right);
            final long lResult = left.join();
            final long rRight = right.join();
            sum = lResult + rRight;
        }
        return sum;
    }

    public static void main(final String[] args) {
        long s = System.currentTimeMillis();
        final ForkJoinPool pool = ForkJoinPool.commonPool();
        final TestForkJoin TestForkJoin = new TestForkJoin(1, LOOPEND);
        final Future<Long> result = pool.submit(TestForkJoin);
        if (!((ForkJoinTask<Long>) result).isCompletedAbnormally()) {
            try {
                System.out.println("fork/join result：" + result.get());
            } catch (final InterruptedException e) {
                e.printStackTrace();
            } catch (final ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println("fork/join time：" + (System.currentTimeMillis() - s) + "ms");

        s = System.currentTimeMillis();
        long sum = 0;
        for(int i = 1; i <= LOOPEND ; i++) {
            sum += i;
        }
        System.out.println("normal result：" + sum);
        System.out.println("normal time：" + (System.currentTimeMillis() - s) + "ms");
    }
}