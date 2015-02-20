package yanglifan.learn.concurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionHandlerDemo {
    private final static Logger LOGGER = LoggerFactory.getLogger("Default Exception Handler");

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            LOGGER.error("======== NOTE ========", e);
        });
        Executors.newSingleThreadExecutor().execute(() -> {
            throw new RuntimeException("My runtime exception");
        });

        Future f = Executors.newSingleThreadExecutor().submit((Runnable) () -> {
            System.out.println("submit task");
            throw new RuntimeException("My runtime exception");
        });
//        f.get();
    }
}
