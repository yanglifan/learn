package yanglifan.learn.concurrency.daemonthread;

import java.util.Date;
import java.util.Deque;

public class CleanerTask extends Thread {
    private Deque<Event> deque;

    public CleanerTask(Deque<Event> deque) {
        this.deque = deque;
        setDaemon(true);
    }

    @Override
    public void run() {
        while (true) {
            clean();
        }
    }

    private void clean() {
        long difference;
        boolean delete;
        if (deque.isEmpty()) {
            return;
        }

        delete = false;
        Date now = new Date();
        do {
            Event e = deque.getLast();
            difference = now.getTime() - e.getDate().getTime();
            if (difference > 10000) {
                System.out.printf("Cleaner: %s\n", e.getEvent());
                deque.removeLast();
                delete = true;
            }
        } while (difference > 10000);

        if (delete) {
            System.out.printf("Cleaner: Size of the queue: %d\n", deque.size());
        }
    }
}
