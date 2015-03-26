package yanglifan.learn.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Before;
import org.junit.Test;

public class CyclicBarrierTest {
    private ExecutorService executorService;

    @Before
    public void setUp() throws Exception {
        executorService = Executors.newCachedThreadPool();

    }

    @Test
    public void test_await() {
//        final AtomicInteger count = new AtomicInteger(0);
//        final CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> {
//            System.out.println("CyclicBarrier - Count: " + count.intValue());
//        });
//        for (int i = 0; i < 4; i++) {
//            executorService.execute(() -> {
//                try {
//                    count.getAndIncrement();
//                    cyclicBarrier.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (BrokenBarrierException e) {
//                    e.printStackTrace();
//                }
//            });
//        }

    }
}
