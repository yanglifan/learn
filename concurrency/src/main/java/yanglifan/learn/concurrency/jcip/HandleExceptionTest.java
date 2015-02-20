package yanglifan.learn.concurrency.jcip;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HandleExceptionTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(HandleExceptionTest.class);

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor(new LogExceptionThreadFactory());

//        executorService = Executors.newSingleThreadExecutor();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                throw new RuntimeException("haha");
            }
        });

        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);
    }
}
