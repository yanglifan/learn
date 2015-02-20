package yanglifan.learn.concurrency.threadpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Lifan on 2/11/2015.
 */
public class ThreadPoolOutOfMemory {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(0, 5, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if (i % 1000000 == 0) {
                System.out.println(executor.getQueue().size());
            }
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    // empty
                }
            });
        }
    }
}
