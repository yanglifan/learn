package yanglifan.learn.concurrency.threadpool;

import java.util.concurrent.*;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ExecutorBuilderTest {
    @Test
    public void testBuildThreadPoolExecutor() throws Exception {
        ExecutorService executorService = ExecutorBuilder.newBuilder().corePoolSize(10).build("MyThread");
        assertThat(((ThreadPoolExecutor) executorService).getCorePoolSize(), is(10));
        Future<String> future = executorService.submit(() -> Thread.currentThread().getName());
        assertTrue(future.get().contains("MyThread-"));
    }

    @Test
    public void test_max_size() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) ExecutorBuilder.newBuilder().maxPoolSize(10).build();
        assertThat(executor.getMaximumPoolSize(), is(10));
    }

    @Test
    public void test_work_queue() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) ExecutorBuilder.newBuilder().workQueue(new ArrayBlockingQueue<>(100)).build();
        assertTrue(executor.getQueue() instanceof ArrayBlockingQueue);
    }

    @Test
    public void testBuildScheduleThreadPoolExecutor() throws Exception {
        ScheduledExecutorService scheduler = ExecutorBuilder.newBuilder().corePoolSize(10).buildScheduled("MyThread");
        assertThat(((ThreadPoolExecutor) scheduler).getCorePoolSize(), is(10));
        Future<String> future = scheduler.submit(() -> Thread.currentThread().getName());
        assertTrue(future.get().contains("MyThread-"));
        assertTrue(scheduler instanceof ScheduledThreadPoolExecutor);
    }
}