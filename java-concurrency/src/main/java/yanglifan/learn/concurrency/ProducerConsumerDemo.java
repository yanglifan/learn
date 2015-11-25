package yanglifan.learn.concurrency;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ProducerConsumerDemo {
    private static final int MAX = 10;
    private final int[] q = new int[MAX];
    private int size;
    private int proIdx;
    private int conIdx;

    public void produce() throws InterruptedException {
        synchronized (q) {
            while (size == MAX) {
                q.wait();
            }

            int value = doProduce();
            System.out.println(Thread.currentThread().getName() + " produces a value [" + value + "]");
            q[proIdx++ % MAX] = value;
            size++;
            q.notifyAll();
        }
    }

    public void consume() throws InterruptedException {
        synchronized (q) {
            while (size == 0) {
                q.wait();
            }

            int value = q[conIdx++ % MAX];
            size--;
            System.out.println(Thread.currentThread().getName() + " consumes a value + [" + value + "]");
            q.notifyAll();
        }
    }

    private int doProduce() {
        Random random = new Random();
        return random.nextInt(10);
    }

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();

        ProducerConsumerDemo producerConsumerDemo = new ProducerConsumerDemo();

        for (int i = 0; i < 50; i++) {
            executorService.execute(() -> {
                try {
                    producerConsumerDemo.produce();
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        for (int i = 0; i < 50; i++) {
            executorService.execute(() -> {
                try {
                    producerConsumerDemo.consume();
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(100, TimeUnit.SECONDS);

        System.out.println("The length of the q " + producerConsumerDemo.size);
    }
}
