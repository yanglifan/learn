package yanglifan.learn.concurrency;

import org.junit.Test;

import java.util.concurrent.Executors;

public class ThreadLocalDemo {
    private final ThreadLocal<String> testThreadLocal = new InheritableThreadLocal<>();

    @Test
    public void inheritedThreadLocal() {
        testThreadLocal.set("value on main thread");
        Executors.newSingleThreadExecutor().execute(() -> {
            System.out.println("Get from the thread local " + testThreadLocal.get());
        });
    }
}
