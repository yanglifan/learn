package yanglifan.learn.concurrency.threadpool;

import org.junit.Test;

import java.util.concurrent.*;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CompletionServiceDemo {
    @Test
    public void basic() throws InterruptedException, ExecutionException {
        ExecutorService executorService = ExecutorBuilder.newBuilder().build();
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(executorService);

        IntStream.range(0, 10).forEach(i -> completionService.submit(() -> {
            Thread.sleep(1000);
            return i;
        }));

        Future<Integer> future = completionService.poll(3, TimeUnit.SECONDS); // InterruptedException
        int sum = 0;
        while (future != null) {
            sum += future.get(); // ExecutionException
            future = completionService.poll(3, TimeUnit.SECONDS);
        }

        assertThat(sum, is(45));
    }
}
