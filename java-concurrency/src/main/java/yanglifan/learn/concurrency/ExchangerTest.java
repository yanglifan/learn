package yanglifan.learn.concurrency;

import java.util.concurrent.Exchanger;

public class ExchangerTest {

    Exchanger<String> exchanger = new Exchanger<>();

    public static void main(String[] args) {
        new ExchangerTest().start();
    }

    void start() {
        new Thread(new ARunnable()).start();
        new Thread(new BRunnable()).start();
    }

    class ARunnable implements Runnable {
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.println("println in A: " + exchanger.exchange("String from ARunnable " + i));
                }
            } catch (InterruptedException ex) {
            }
        }
    }

    class BRunnable implements Runnable {
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.println("println in B: " + exchanger.exchange("String from BRunnable " + i));
                }
            } catch (InterruptedException ex) {
            }
        }
    }
}
