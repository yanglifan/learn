package yanglifan.learn.concurrency.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

import org.junit.Test;

public class RejectedExecutionHandlerDemo {

    @Test(expected = RejectedExecutionException.class)
    public void test_abort_policy() {
        ExecutorService executor = ExecutorBuilder.newBuilder().corePoolSize(1)
                .workQueue(new ArrayBlockingQueue<>(1)).build();
        executor.execute(() -> {
            try {
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        executor.execute(() -> System.out.println("Empty task"));
        executor.execute(() -> System.out.println("Empty task"));
        System.out.println("Main thread exec");
    }
}
