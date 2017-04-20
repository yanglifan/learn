package yanglifan.learn.concurrency.threadpool;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.*;

public class SingleThreadPoolDemo {
    ExecutorService executor;

    @Before
    public void setUp() throws Exception {
        executor = Executors.newSingleThreadExecutor();
    }

    @After
    public void tearDown() throws Exception {
        executor.shutdown();
        executor.awaitTermination(6, TimeUnit.SECONDS);
    }

    @Test
    public void sleep_park_thread() {

        executor.execute(() -> {
            try {
                System.out.println(new Date());
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("The first task exec at " + new Date());
        });
        executor.execute(() -> {
            System.out.println("The second task exec at " + new Date());
        });
    }

    @Test
    public void wait_park_thread() {
        final Object lock = new Object();
        executor.execute(() -> {
            synchronized (lock) {
                try {
                    System.out.println(new Date());
                    lock.wait(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("The first task exec at " + new Date());
        });

        executor.execute(() -> {
            System.out.println("The second task exec at " + new Date());
        });
    }

    @Test(expected = ClassCastException.class)
    public void change_single_thread_pool_core_size() {
        ExecutorService single = Executors.newSingleThreadExecutor();
        ((ThreadPoolExecutor) single).setCorePoolSize(2);
    }

    @Test(expected = ClassCastException.class)
    public void unconfigurable() {
        ExecutorService source = ExecutorBuilder.newBuilder().build();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.unconfigurableExecutorService(source);
        executor.setCorePoolSize(2);
    }

    @Test
    public void test_submit() throws Exception {
        ExecutorService executorService = ExecutorBuilder.newBuilder().build();
        Future<String> f = executorService.submit(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "success";
        });

        f.get();
    }
}
