import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author liming.gong
 */
public class UseCompletableFuture {
    public static void main(String[] args) throws Exception {
        CompletableFuture.supplyAsync(() -> "hello ").thenApply(s -> s + "world!")
                .whenComplete((r, e) -> System.out.println(r));

        CompletableFuture.supplyAsync(() -> "hello ").thenApply(s -> s + "world!")
                .whenComplete((r, e) -> System.out.println(r));

        CompletableFuture.supplyAsync(() -> "hello ").thenApply(s -> s + "world!")
                .thenCombine(CompletableFuture.completedFuture("ok"), (h, t) -> h + t)
                .thenAccept(System.out::println);

        CompletableFuture.supplyAsync(() -> 1).thenApply(i -> i + 1).thenApply(i -> i * i)
                .whenComplete((r, e) -> System.out.println(r));

        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<Integer> future = service.submit(() -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 2;
        });
        System.out.println(future.isDone());
        System.out.println(future.get());
        service.shutdown();

        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
            assert Thread.currentThread().isDaemon();
        });
        assert cf.isDone();

        String[] list = {"a", "b", "c"};
        List<CompletableFuture<String>> completableFutureList = new ArrayList<>();
        for (String s : list) {
            completableFutureList.add(CompletableFuture.supplyAsync(() -> s)
                    .thenApply(String::toUpperCase));
        }
        CompletableFuture<String>[] a = completableFutureList
                .toArray(new CompletableFuture[completableFutureList.size()]);
        System.out.println(a.length);
        CompletableFuture<Void> f = CompletableFuture.allOf(a);
        f.join();
        System.out.println(f.get());

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "wine");
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "cafe");
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> "tea");

        CompletableFuture.allOf(future1, future2, future3)
                .thenApply(x ->
                        Stream.of(future1, future2, future3)
                                .map(CompletableFuture::join)
                                .collect(Collectors.joining(" ")))
                .thenAccept(System.out::print);
        System.out.println();
        CompletableFuture<Object> future4 = CompletableFuture.anyOf(future1, future2, future3);

        try {
            System.out.println(future4.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        CompletableFuture.supplyAsync(() -> "hello world")
                .thenApply(s -> {
                    s = null;
                    return s.length();
                }).thenAccept(System.out::println)
                .exceptionally(e -> {
                    System.out.println("捕获异常:" + e.getMessage());
                    return null;
                });
    }
}