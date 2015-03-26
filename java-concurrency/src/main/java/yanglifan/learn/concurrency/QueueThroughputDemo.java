package yanglifan.learn.concurrency;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class QueueThroughputDemo {
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private AtomicInteger putSum = new AtomicInteger(0);
    private AtomicInteger takeSum = new AtomicInteger(0);

    private int numOfActionPerThread;
    private int numOfThreads;
    private CyclicBarrier barrier;
    private BlockingQueue<Integer> queue;

    private BarrierTimer barrierTimer = new BarrierTimer();

    public static QueueThroughputDemo newInstance(BlockingQueue<Integer> blockingQueue) {
        QueueThroughputDemo instance = new QueueThroughputDemo();
        instance.queue = blockingQueue;
        return instance;
    }

    private static final int ACTION_NUM = 2000000;
    private static final int THREAD_NUM = 100;

    private static QueueThroughputDemo newTester(BlockingQueue<Integer> blockingQueue) {
        return QueueThroughputDemo
                .newInstance(blockingQueue)
                .setNumOfActionPerThread(ACTION_NUM)
                .setNumOfThreads(THREAD_NUM);
    }

    public static void main(String[] args) throws Exception {
        testArrayBlockingQueueThroughput();
        testLinkedBlockingQueueThroughput();
        testLinkedTransferQueueThroughput();
    }

    private static void testLinkedTransferQueueThroughput() throws Exception {
        newTester(new LinkedTransferQueue<>()).test();
    }

    private static void testLinkedBlockingQueueThroughput() throws Exception {
        newTester(new LinkedBlockingQueue<>()).test();
    }

    private static void testArrayBlockingQueueThroughput() throws Exception {
        newTester(new ArrayBlockingQueue<>(ACTION_NUM)).test();
    }

    static int xorShift(int y) {
        y ^= (y << 6);
        y ^= (y >>> 21);
        y ^= (y << 7);
        return y;
    }

    public QueueThroughputDemo setNumOfActionPerThread(int numOfActionPerThread) {
        System.out.println("The number of actions executed by per thread is " + numOfActionPerThread);
        this.numOfActionPerThread = numOfActionPerThread;
        return this;
    }

    public QueueThroughputDemo setNumOfThreads(int numOfThreads) {
        System.out.println("The number of threads is " + numOfThreads);
        this.numOfThreads = numOfThreads;
        barrier = new CyclicBarrier(numOfThreads * 2 + 1, barrierTimer);
        return this;
    }

    public void test() throws Exception {
        for (int i = 0; i < numOfThreads; i++) {
            executorService.execute(new Producer());
            executorService.execute(new Consumer());
        }

        barrier.await(); // wait for all threads to be ready
        barrier.await(); // wait for all threads to finish

        System.out.println("PutSum: " + putSum.get());
        System.out.println("TakeSum: " + takeSum.get());

        if (putSum.get() != takeSum.get()) {
            throw new RuntimeException("Test failed, PutSum not equal with Take Sum");
        }

        System.out.println(queue.getClass().getSimpleName() + " consume: " + barrierTimer.getTime() + " ms");
    }

    class Producer implements Runnable {

        public void run() {
            try {
                int seed = (this.hashCode() ^ (int) System.nanoTime()) / 10000;
                int sum = 0;
                barrier.await();
                for (int i = numOfActionPerThread; i > 0; --i) {
                    if (queue instanceof TransferQueue) {
                        TransferQueue<Integer> transferQueue = (TransferQueue<Integer>) queue;
//                        if (!transferQueue.tryTransfer(seed, 1, TimeUnit.MILLISECONDS)) {
                        transferQueue.transfer(seed);
//                        }
                    } else {
                        queue.put(seed);
                    }

                    sum += seed;
                    seed = xorShift(seed);
                }
                putSum.getAndAdd(sum);
                barrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    class Consumer implements Runnable {
        public void run() {
            try {
                barrier.await();
                int sum = 0;
                for (int i = numOfActionPerThread; i > 0; --i) {
                    sum += queue.take();
                }
                takeSum.getAndAdd(sum);
                barrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
