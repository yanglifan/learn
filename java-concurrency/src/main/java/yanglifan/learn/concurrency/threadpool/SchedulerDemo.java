package yanglifan.learn.concurrency.threadpool;

import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Yang Lifan
 */
public class SchedulerDemo {
    @Test
    public void demo_exception() throws Exception {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);
        AtomicInteger count = new AtomicInteger();
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println(count.getAndIncrement());
            if (count.get() == 8)
                throw new RuntimeException("test");
        }, 0, 1, TimeUnit.SECONDS);
        Thread.sleep(10000);
    }
}
