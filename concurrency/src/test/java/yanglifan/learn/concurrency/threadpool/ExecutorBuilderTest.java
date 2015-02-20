package yanglifan.learn.concurrency.threadpool;

import java.util.concurrent.*;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ExecutorBuilderTest {
    @Test
    public void testBuildThreadPoolExecutor() throws Exception {
        ExecutorService executorService = ExecutorBuilder.newBuilder().corePoolSize(10).threadNamePrefix("MyThread-").build(ExecutorService.class);
        assertThat(((ThreadPoolExecutor) executorService).getCorePoolSize(), is(10));
        Future<String> future = executorService.submit(() -> Thread.currentThread().getName());
        assertTrue(future.get().contains("MyThread-"));
    }

    @Test
    public void testBuildScheduleThreadPoolExecutor() throws Exception {
        ExecutorService executorService = ExecutorBuilder.newBuilder().corePoolSize(10).threadNamePrefix("MyThread-").build(ScheduledExecutorService.class);
        assertThat(((ThreadPoolExecutor) executorService).getCorePoolSize(), is(10));
        Future<String> future = executorService.submit(() -> Thread.currentThread().getName());
        assertTrue(future.get().contains("MyThread-"));
        assertTrue(executorService instanceof ScheduledThreadPoolExecutor);
    }
}