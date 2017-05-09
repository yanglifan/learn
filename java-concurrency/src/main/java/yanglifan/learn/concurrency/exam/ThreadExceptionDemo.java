package yanglifan.learn.concurrency.exam;

import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadExceptionDemo {
    @Test
    public void scheduler_exception() {
        Executors.newScheduledThreadPool(1).schedule(new Runnable() {
            @Override
            public void run() {
                throw new RuntimeException("test");
            }
        }, 1, TimeUnit.SECONDS);
    }
}
