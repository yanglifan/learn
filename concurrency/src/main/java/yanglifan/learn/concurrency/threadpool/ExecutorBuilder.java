package yanglifan.learn.concurrency.threadpool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorBuilder {
    private int corePoolSize;
    private int maxPoolSize;
    private InternalThreadFactory threadFactory;

    public static ExecutorBuilder newBuilder() {
        return new ExecutorBuilder();
    }

    public ExecutorBuilder corePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
        return this;
    }

    public ExecutorBuilder maxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
        return this;
    }

    public ExecutorBuilder threadNamePrefix(String prefix) {
        initThreadFactoryIfNecessary();
        threadFactory.setThreadNamePrefix(prefix);
        return this;
    }

    private void initThreadFactoryIfNecessary() {
        if (threadFactory == null) {
            threadFactory = new InternalThreadFactory();
        }
    }

    @SuppressWarnings("all")
    public <T extends ExecutorService> T build(Class<T> executorClazz) {
        maxPoolSize = maxPoolSize == 0 ? corePoolSize : maxPoolSize;

        ThreadPoolExecutor threadPoolExecutor = null;
        if (executorClazz.equals(ExecutorService.class)) {
            threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        } else if (executorClazz.equals(ScheduledExecutorService.class)) {
            threadPoolExecutor = new ScheduledThreadPoolExecutor(corePoolSize);
        }

        if (threadPoolExecutor == null)
            throw new IllegalArgumentException("Only ExecutorService or ScheduledExecutorService");

        if (threadFactory != null)
            threadPoolExecutor.setThreadFactory(threadFactory);

        return (T) threadPoolExecutor;
    }

    private class InternalThreadFactory implements ThreadFactory {
        private ThreadFactory baseThreadFactory = Executors.defaultThreadFactory();
        private AtomicInteger threadCount = new AtomicInteger(1);

        private String threadNamePrefix;


        public InternalThreadFactory() {
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = baseThreadFactory.newThread(r);
            if (threadNamePrefix != null)
                thread.setName(threadNamePrefix + threadCount.getAndIncrement());
            return thread;
        }

        public void setThreadNamePrefix(String threadNamePrefix) {
            this.threadNamePrefix = threadNamePrefix;
        }
    }
}
