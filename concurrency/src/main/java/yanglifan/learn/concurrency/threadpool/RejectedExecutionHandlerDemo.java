package yanglifan.learn.concurrency.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RejectedExecutionHandlerDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(RejectedExecutionHandlerDemo.class);

    private ThreadPoolExecutor executor;

    @Before
    public void setUp() throws Exception {
        executor = (ThreadPoolExecutor) ExecutorBuilder.newBuilder().corePoolSize(1)
                .workQueue(new ArrayBlockingQueue<>(1)).build();
    }

    @After
    public void tearDown() throws Exception {
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test(expected = RejectedExecutionException.class)
    public void test_abort_policy() throws Exception {
        invokeRejectHandler();
        System.out.println("Main thread exec");
    }

    @Test
    public void test_caller_runs_policy() throws Exception {
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        invokeRejectHandler();
    }

    @Test(expected = RejectedExecutionException.class)
    public void exec_after_shutdown() throws Exception {
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.execute(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executor.shutdown();
        executor.execute(() -> {
            System.out.println("should be reject");
        });
    }

    private void invokeRejectHandler() throws Exception {
        executor.execute(() -> {
            try {
                LOGGER.info("Sleep 3s");
                Thread.sleep(3000);
                LOGGER.info("Sleep task");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        executor.execute(() -> LOGGER.info("Task 1"));
        LOGGER.info("Before Task 2");
        executor.execute(() -> LOGGER.info("Task 2"));
    }

}
