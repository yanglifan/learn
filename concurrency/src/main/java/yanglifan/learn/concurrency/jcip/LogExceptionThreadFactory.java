package yanglifan.learn.concurrency.jcip;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogExceptionThreadFactory implements ThreadFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger("DEFAULT LOGGER");
    private static final ThreadFactory DEFAULT_THREAD_FACTORY = Executors.defaultThreadFactory();

    @Override
    public Thread newThread(Runnable r) {
        if (r == null) throw new NullPointerException();

        Thread t = DEFAULT_THREAD_FACTORY.newThread(r);

        t.setUncaughtExceptionHandler((thread, e) -> {
            LOGGER.error("Uncaught Exception:", e);
//            e.printStackTrace();
        });

        return t;
    }
}
