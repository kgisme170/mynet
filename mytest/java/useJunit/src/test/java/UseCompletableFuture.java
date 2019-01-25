import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author liming.gong
 */
public class UseCompletableFuture {
    public static void main(String[] args) throws Exception {
        CompletableFuture<String> contents = new CompletableFuture<>();
        contents.supplyAsync(() -> "hello ").thenApply(s -> s + "world!")
                .whenComplete((r, e) -> System.out.println(r));

        CompletableFuture.supplyAsync(() -> "hello ").thenApply(s -> s + "world!")
                .whenComplete((r, e) -> System.out.println(r));

        CompletableFuture.supplyAsync(() -> "hello ").thenApply(s -> s + "world!")
                .thenCombine(CompletableFuture.completedFuture("ok"), (h, t) -> h + t )
                .thenAccept(System.out::println);

        CompletableFuture.supplyAsync(() -> 1).thenApply(i -> i + 1).thenApply(i -> i * i)
                .whenComplete((r, e) -> System.out.println(r));

        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<Integer> future = service.submit(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 2;
        });
        System.out.println(future.get());
        service.shutdown();
    }
}