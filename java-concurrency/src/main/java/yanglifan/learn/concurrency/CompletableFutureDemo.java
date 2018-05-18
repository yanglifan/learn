package yanglifan.learn.concurrency;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;

/**
 * @author Yang Lifan
 */
public class CompletableFutureDemo {
    @Test
    public void test_then_apply() {
        CompletableFuture.supplyAsync(() -> "Hello")
                .thenApply(String::length)
                .whenComplete((i, e) -> System.out.println(i));
    }
}
