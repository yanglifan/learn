package yanglifan.learn.concurrency.threadpool;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SingleThreadPoolSleep {
    public static void main(String[] args) {
        Executor executor = Executors.newSingleThreadExecutor();
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
}
