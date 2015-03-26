package yanglifan.learn.concurrency.dsl;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

public class FirstStep extends AbstractStep {
    private Callable callable;

    public FirstStep(ExecutorService executorService, Callable callable) {
        super(executorService);
        this.callable = callable;
    }

    public Callable getCallable() {
        return callable;
    }
}
