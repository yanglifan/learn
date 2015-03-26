package yanglifan.learn.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class ExecutorServiceTest {
    @Test
    public void shutdown_then_execute() throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> {
            try {
                System.out.println("A runnable executes before sleep...");
                Thread.sleep(5000);
                System.out.println("A runnable executes after sleep...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();

        try {
            executorService.execute(() -> {
                System.out.println("A runnable executes after shutdown");
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        executorService.awaitTermination(10000, TimeUnit.SECONDS);
    }

    @Test
    public void shutdownNow_then_execute() throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> {
            try {
                System.out.println("A runnable executes before sleep...");
                Thread.sleep(5000);
                System.out.println("A runnable executes after sleep...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdownNow();

        try {
            executorService.execute(() -> {
                System.out.println("A runnable executes after shutdown");
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        executorService.awaitTermination(10000, TimeUnit.SECONDS);
    }
}
