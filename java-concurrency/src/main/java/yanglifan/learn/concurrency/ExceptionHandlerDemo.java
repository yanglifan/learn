package yanglifan.learn.concurrency;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExceptionHandlerDemo {
    private final static Logger LOGGER = LoggerFactory.getLogger("Default Exception Handler");

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
//            LOGGER.error("======== NOTE ========", e);
//        });
        Executors.newSingleThreadExecutor().execute(() -> {
            throw new RuntimeException("My runtime exception");
        });

        Future f = Executors.newSingleThreadExecutor().submit((Runnable) () -> {
            System.out.println("submit task");
            throw new RuntimeException("My runtime exception");
        });
//        f.get();
    }

    @Test
    public void ex_handle_in_thread() throws Exception {
        Thread t = new Thread(() -> System.out.println(1 / 0));
        t.start();
        Thread.sleep(10000000);
    }

    @Test
    public void output_ex_to_log() {
        Thread t = new Thread(() -> {
            try {
                System.out.println(1 / 0);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        });
        t.start();
    }

    @Test
    public void submit_ex() {

    }
}
