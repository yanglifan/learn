package yanglifan.learn.concurrency.threadpool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorBuilder {
    private int corePoolSize;
    private int maxPoolSize;
    private InternalThreadFactory threadFactory;
    private BlockingQueue<Runnable> workQueue;

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

    public ExecutorBuilder workQueue(BlockingQueue<Runnable> queue) {
        this.workQueue = queue;
        return this;
    }

    private void initThreadFactoryIfNecessary() {
        if (threadFactory == null) {
            threadFactory = new InternalThreadFactory();
        }
    }

    public ExecutorService build() {
        return build(null);
    }

    public ExecutorService build(String name) {
        if (name != null) {
            initThreadFactoryIfNecessary();
            threadFactory.setThreadNamePrefix(name);
        }

        if (corePoolSize == 0)
            corePoolSize = Runtime.getRuntime().availableProcessors();

        maxPoolSize = maxPoolSize == 0 ? corePoolSize : maxPoolSize;

        if (workQueue == null) {
            workQueue = new LinkedBlockingQueue<>();
        }

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 0, TimeUnit.SECONDS, workQueue);

        if (threadFactory != null)
            threadPoolExecutor.setThreadFactory(threadFactory);

        return threadPoolExecutor;
    }

    public ScheduledExecutorService buildScheduled() {
        return buildScheduled(null);
    }

    public ScheduledExecutorService buildScheduled(String name) {
        if (name != null) {
            initThreadFactoryIfNecessary();
            threadFactory.setThreadNamePrefix(name);
        }

        ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(corePoolSize);

        if (threadFactory != null)
            scheduler.setThreadFactory(threadFactory);

        return scheduler;
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
                thread.setName(threadNamePrefix + "-" + threadCount.getAndIncrement());
            return thread;
        }

        public void setThreadNamePrefix(String threadNamePrefix) {
            this.threadNamePrefix = threadNamePrefix;
        }
    }
}
