package yanglifan.learn.concurrency;

import java.util.ConcurrentModificationException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

public class ConcurrentModificationExceptionTest {

    @Test(expected = ConcurrentModificationException.class)
    public void synchronized_collection_loop() {
        final Hashtable<String, String> hashtable = new Hashtable<String, String>() {{
            for (int i = 0; i < 100; i++) {
                put(i + "", i + "");
            }
        }};

        Iterator iterator = hashtable.entrySet().iterator();

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                hashtable.remove("1");
            }
        });

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }


}
