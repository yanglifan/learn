package yanglifan.learn.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WrongInterruptHandler {
    private volatile Object flag;
    private Lock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();

    public static void main(String[] args) {
        WrongInterruptHandler handler = new WrongInterruptHandler();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future future = executorService.submit(new Runnable() {
            @Override
            public void run() {
                handler.wrong();
            }
        });

        System.out.println("Try to cancel");
        future.cancel(true);
    }

    public void wrong() {
        lock.lock();
        try {
            while (flag == null) {
                try {
                    System.out.println("Begin to wait");
                    notFull.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Interrupt all the same!");
                }
            }
        } finally {
            lock.unlock();
        }
    }
}
