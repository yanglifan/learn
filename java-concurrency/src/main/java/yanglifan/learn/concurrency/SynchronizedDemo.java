package yanglifan.learn.concurrency;

import org.junit.Test;

public class SynchronizedDemo {
    int i = 0;

    public synchronized void add() {
        for (int j = 0; j < 1000; j++) {
            System.out.println(Thread.currentThread().getName() + " add :: i = " + i++);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void get() {
        System.out.println(Thread.currentThread().getName() + " get :: i = " + i);
    }

    @Test
    public void test() throws Exception {
        final SynchronizedDemo demo = new SynchronizedDemo();
        Thread t1 = new Thread(() -> demo.add());
        t1.start();

        Thread t2 = new Thread(() -> {
            while (true) {
                demo.get();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t2.start();

        Thread.sleep(1000000);
    }
}
