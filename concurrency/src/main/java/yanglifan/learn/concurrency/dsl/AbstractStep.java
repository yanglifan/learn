package yanglifan.learn.concurrency.dsl;

import java.util.concurrent.ExecutorService;

public abstract class AbstractStep {
    private ExecutorService executorService;

    protected AbstractStep(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }
}
