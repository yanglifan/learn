package yanglifan.learn.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

public class Worker implements Runnable {
    private Phaser phaser;
    private String name;
    private int sleepTime;

    public Worker(Phaser phaser, String name, int sleepTime) {
        this.name = name;
        this.phaser = phaser;
        this.sleepTime = sleepTime;
        System.out.println(name + " register " + this.phaser.register());
    }

    public static void main(String[] args) {
        Phaser deliverPhaser = new Phaser(1);
        System.out.println("Start to process the order");
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new Worker(deliverPhaser, "cook", 1));
        executorService.execute(new Worker(deliverPhaser, "waiter", 2));
        executorService.execute(new Worker(deliverPhaser, "attendent", 5));
        for (int i = 1; i <= 3; i++) {
            deliverPhaser.arriveAndAwaitAdvance();
            System.out.println("Order " + i + " has been finished");
        }
        deliverPhaser.arriveAndDeregister();
        System.out.println("The whole is finished");
        executorService.shutdown();
    }

    @Override
    public void run() {
        for (int i = 1; i <= 3; i++) {
            System.out.println(name + " doing this work for the order " + i);

            try {
                System.out.println(name + " will work for " + sleepTime + " s");
                Thread.sleep(sleepTime * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (i == 3) {
                phaser.arriveAndDeregister();
            } else {
                System.out.println(name + " " + phaser.arriveAndAwaitAdvance());
                System.out.println(name + " after await");
            }
        }
    }
}
