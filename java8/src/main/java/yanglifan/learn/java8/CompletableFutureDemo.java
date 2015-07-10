package yanglifan.learn.java8;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompletableFutureDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompletableFutureDemo.class);

    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Test
    public void test_then_accept() throws Exception {
        final CompletableFuture<String> responseFuture = asyncCall();
        Thread.sleep(10_000);
        responseFuture.thenAccept(this::send);
        LOGGER.info("After thenAccept");
    }

    @Test
    public void test_then_accept_async() throws Exception {
        final CompletableFuture<String> responseFuture = asyncCall();
        Thread.sleep(10_000);
        responseFuture.thenAcceptAsync(this::send);
        LOGGER.info("After thenAccept");
        Thread.sleep(5_000);
    }

    @Test
    public void test_exceptionally() throws Exception {
        final CompletableFuture<String> responseFuture = asyncCall(true);
        responseFuture.exceptionally(ex -> {
            LOGGER.error("Exception happen", ex);
            return "Failed";
        }).thenAcceptAsync(this::send);
        LOGGER.info("After thenAccept");
        Thread.sleep(10_000);
    }

    /**
     * Select the first finished task and get the return to execute.
     *
     * {@link CompletableFuture#applyToEither(CompletionStage, Function)} will apply the first finished task to a
     * {@link Function} {@link CompletableFuture#acceptEither(CompletionStage, Consumer)} will use a {@link Consumer}
     */
    @Test
    public void test_accept_either() throws Exception {
        final CompletableFuture<String> future = asyncCall("First call", 2000);
        CompletableFuture<String> anotherFuture = asyncCall("Second call", 1000);

        future.acceptEither(anotherFuture, (msg) -> {
           LOGGER.info("The msg returned is {}", msg);
        });

        Thread.sleep(10_000);
    }

    private void send(String s) {
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info("Send a message [ {} ]", s);
    }

    private CompletableFuture<String> asyncCall(String msg, int sleepTime) {
        return CompletableFuture.supplyAsync(() -> {
            LOGGER.info("Start -> supplyAsync");
            try {
                Thread.sleep(sleepTime
                );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            LOGGER.info("End -> supplyAsync");
            return msg;
        }, executorService);
    }

    private CompletableFuture<String> asyncCall() {
        return asyncCall(false);
    }

    private CompletableFuture<String> asyncCall(boolean withEx) {
        return CompletableFuture.supplyAsync(() -> {
            LOGGER.info("Start -> supplyAsync");
            try {
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (withEx) {
                throw new RuntimeException("I am evil");
            }

            LOGGER.info("End -> supplyAsync");
            return "ASYNC MSG";
        }, executorService);
    }
}
